package com.nomnom.backend.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.nomnom.backend.dto.CreateFolderRequest;
import com.nomnom.backend.dto.FolderDto;
import com.nomnom.backend.entity.Folder;
import com.nomnom.backend.exception.FirestoreException;
import com.nomnom.backend.exception.FolderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@ConditionalOnExpression("'${firebase.configured:false}' == 'true'")
public class FolderService {
    
    private final Firestore firestore;
    private static final String COLLECTION_NAME = "folders";
    
    @Autowired
    public FolderService(Firestore firestore) {
        this.firestore = firestore;
    }
    
    public FolderDto createFolder(CreateFolderRequest request, String userId) {
        if (firestore == null) {
            throw new FirestoreException("Firestore is not configured. Cannot create folder.");
        }
        
        try {
            Folder folder = new Folder(request.getName(), request.getDescription(), userId);
            
            // Save to Firestore
            CollectionReference folders = firestore.collection(COLLECTION_NAME);
            ApiFuture<DocumentReference> future = folders.add(folder);
            DocumentReference documentReference = future.get();
            
            // Set the generated ID
            folder.setId(documentReference.getId());
            
            return convertToDto(folder);
            
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new FirestoreException("Error creating folder: " + e.getMessage(), e);
        }
    }
    
    public List<FolderDto> getFoldersByUserId(String userId) {
        if (firestore == null) {
            throw new FirestoreException("Firestore is not configured. Cannot fetch folders.");
        }
        
        try {
            CollectionReference folders = firestore.collection(COLLECTION_NAME);
            Query query = folders.whereEqualTo("userId", userId);
            ApiFuture<QuerySnapshot> future = query.get();
            
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            List<FolderDto> folderDtos = new ArrayList<>();
            
            for (QueryDocumentSnapshot document : documents) {
                Folder folder = document.toObject(Folder.class);
                folder.setId(document.getId());
                folderDtos.add(convertToDto(folder));
            }
            
            return folderDtos;
            
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new FirestoreException("Error fetching folders: " + e.getMessage(), e);
        }
    }
    
    public FolderDto getFolderById(String folderId, String userId) {
        if (firestore == null) {
            throw new FirestoreException("Firestore is not configured. Cannot fetch folder.");
        }
        
        try {
            DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(folderId);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();
            
            if (!document.exists()) {
                throw new FolderNotFoundException("Folder not found with id: " + folderId);
            }
            
            Folder folder = document.toObject(Folder.class);
            if (folder == null) {
                throw new FolderNotFoundException("Folder not found with id: " + folderId);
            }
            
            // Check if user owns this folder
            if (!userId.equals(folder.getUserId())) {
                throw new FolderNotFoundException("Folder not found with id: " + folderId);
            }
            
            folder.setId(document.getId());
            return convertToDto(folder);
            
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new FirestoreException("Error fetching folder: " + e.getMessage(), e);
        }
    }
    
    public FolderDto updateFolder(String folderId, CreateFolderRequest request, String userId) {
        if (firestore == null) {
            throw new FirestoreException("Firestore is not configured. Cannot update folder.");
        }
        
        try {
            // First check if folder exists and user owns it
            FolderDto existingFolder = getFolderById(folderId, userId);
            
            // Update the folder
            DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(folderId);
            ApiFuture<WriteResult> future = docRef.update(
                "name", request.getName(),
                "description", request.getDescription(),
                "updatedAt", LocalDateTime.now()
            );
            
            future.get(); // Wait for completion
            
            // Return updated folder
            return getFolderById(folderId, userId);
            
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new FirestoreException("Error updating folder: " + e.getMessage(), e);
        }
    }
    
    public void deleteFolder(String folderId, String userId) {
        if (firestore == null) {
            throw new FirestoreException("Firestore is not configured. Cannot delete folder.");
        }
        
        try {
            // First check if folder exists and user owns it
            getFolderById(folderId, userId);
            
            // Delete the folder
            DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(folderId);
            ApiFuture<WriteResult> future = docRef.delete();
            future.get(); // Wait for completion
            
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new FirestoreException("Error deleting folder: " + e.getMessage(), e);
        }
    }
    
    public FolderDto addMealToFolder(String folderId, String mealId, String userId) {
        if (firestore == null) {
            throw new FirestoreException("Firestore is not configured. Cannot add meal to folder.");
        }
        
        try {
            // First check if folder exists and user owns it
            FolderDto existingFolder = getFolderById(folderId, userId);
            
            // Check if meal is already in folder
            if (existingFolder.getMealIds().contains(mealId)) {
                return existingFolder; // Already exists, return as-is
            }
            
            // Add meal to folder
            List<String> updatedMealIds = new ArrayList<>(existingFolder.getMealIds());
            updatedMealIds.add(mealId);
            
            DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(folderId);
            ApiFuture<WriteResult> future = docRef.update(
                "mealIds", updatedMealIds,
                "updatedAt", LocalDateTime.now()
            );
            
            future.get(); // Wait for completion
            
            // Return updated folder
            return getFolderById(folderId, userId);
            
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new FirestoreException("Error adding meal to folder: " + e.getMessage(), e);
        }
    }
    
    public FolderDto removeMealFromFolder(String folderId, String mealId, String userId) {
        if (firestore == null) {
            throw new FirestoreException("Firestore is not configured. Cannot remove meal from folder.");
        }
        
        try {
            // First check if folder exists and user owns it
            FolderDto existingFolder = getFolderById(folderId, userId);
            
            // Remove meal from folder
            List<String> updatedMealIds = new ArrayList<>(existingFolder.getMealIds());
            updatedMealIds.remove(mealId);
            
            DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(folderId);
            ApiFuture<WriteResult> future = docRef.update(
                "mealIds", updatedMealIds,
                "updatedAt", LocalDateTime.now()
            );
            
            future.get(); // Wait for completion
            
            // Return updated folder
            return getFolderById(folderId, userId);
            
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new FirestoreException("Error removing meal from folder: " + e.getMessage(), e);
        }
    }
    
    private FolderDto convertToDto(Folder folder) {
        return new FolderDto(
            folder.getId(),
            folder.getName(),
            folder.getDescription(),
            folder.getUserId(),
            folder.getMealIds(),
            folder.getCreatedAt(),
            folder.getUpdatedAt()
        );
    }
} 