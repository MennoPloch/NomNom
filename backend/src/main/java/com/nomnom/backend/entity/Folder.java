package com.nomnom.backend.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Folder {
    
    @DocumentId
    private String id;
    
    @PropertyName("name")
    private String name;
    
    @PropertyName("description")
    private String description;
    
    @PropertyName("userId")
    private String userId;
    
    @PropertyName("mealIds")
    private List<String> mealIds;
    
    @PropertyName("createdAt")
    private LocalDateTime createdAt;
    
    @PropertyName("updatedAt")
    private LocalDateTime updatedAt;
    
    public Folder() {
        this.mealIds = new ArrayList<>();
    }
    
    public Folder(String name, String description, String userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.mealIds = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public List<String> getMealIds() {
        return mealIds;
    }
    
    public void setMealIds(List<String> mealIds) {
        this.mealIds = mealIds;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Helper methods
    public void addMeal(String mealId) {
        if (!this.mealIds.contains(mealId)) {
            this.mealIds.add(mealId);
            this.updatedAt = LocalDateTime.now();
        }
    }
    
    public void removeMeal(String mealId) {
        if (this.mealIds.remove(mealId)) {
            this.updatedAt = LocalDateTime.now();
        }
    }
} 