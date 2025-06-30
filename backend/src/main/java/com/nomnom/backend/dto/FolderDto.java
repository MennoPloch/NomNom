package com.nomnom.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class FolderDto {
    
    private String id;
    private String name;
    private String description;
    private String userId;
    private List<String> mealIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public FolderDto() {}
    
    public FolderDto(String id, String name, String description, String userId, 
                    List<String> mealIds, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.mealIds = mealIds;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
} 