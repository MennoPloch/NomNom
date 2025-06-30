package com.nomnom.backend.controller;

import com.nomnom.backend.dto.AddMealToFolderRequest;
import com.nomnom.backend.dto.CreateFolderRequest;
import com.nomnom.backend.dto.FolderDto;
import com.nomnom.backend.exception.FirestoreException;
import com.nomnom.backend.exception.FolderNotFoundException;
import com.nomnom.backend.service.FolderService;
import jakarta.validation.Valid;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/folders")
@ConditionalOnExpression("'${firebase.configured:false}' == 'true'")
public class FolderController {
    
    private final FolderService folderService;
    
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }
    
    @PostMapping
    public ResponseEntity<FolderDto> createFolder(
            @Valid @RequestBody CreateFolderRequest request,
            @RequestHeader("X-User-ID") String userId) {
        
        FolderDto folder = folderService.createFolder(request, userId);
        return ResponseEntity.ok(folder);
    }
    
    @GetMapping
    public ResponseEntity<List<FolderDto>> getUserFolders(
            @RequestHeader("X-User-ID") String userId) {
        
        List<FolderDto> folders = folderService.getFoldersByUserId(userId);
        return ResponseEntity.ok(folders);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FolderDto> getFolder(
            @PathVariable String id,
            @RequestHeader("X-User-ID") String userId) {
        
        FolderDto folder = folderService.getFolderById(id, userId);
        return ResponseEntity.ok(folder);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FolderDto> updateFolder(
            @PathVariable String id,
            @Valid @RequestBody CreateFolderRequest request,
            @RequestHeader("X-User-ID") String userId) {
        
        FolderDto folder = folderService.updateFolder(id, request, userId);
        return ResponseEntity.ok(folder);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(
            @PathVariable String id,
            @RequestHeader("X-User-ID") String userId) {
        
        folderService.deleteFolder(id, userId);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/meals")
    public ResponseEntity<FolderDto> addMealToFolder(
            @PathVariable String id,
            @Valid @RequestBody AddMealToFolderRequest request,
            @RequestHeader("X-User-ID") String userId) {
        
        FolderDto folder = folderService.addMealToFolder(id, request.getMealId(), userId);
        return ResponseEntity.ok(folder);
    }
    
    @DeleteMapping("/{id}/meals/{mealId}")
    public ResponseEntity<FolderDto> removeMealFromFolder(
            @PathVariable String id,
            @PathVariable String mealId,
            @RequestHeader("X-User-ID") String userId) {
        
        FolderDto folder = folderService.removeMealFromFolder(id, mealId, userId);
        return ResponseEntity.ok(folder);
    }
    
    // Exception Handlers
    @ExceptionHandler(FolderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleFolderNotFoundException(FolderNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Folder not found");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(FirestoreException.class)
    public ResponseEntity<Map<String, Object>> handleFirestoreException(FirestoreException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Database Error");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Invalid Request");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", "An unexpected error occurred");
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
} 