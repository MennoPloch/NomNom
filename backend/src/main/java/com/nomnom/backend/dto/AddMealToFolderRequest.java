package com.nomnom.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class AddMealToFolderRequest {
    
    @NotBlank(message = "Meal ID is required")
    private String mealId;
    
    public AddMealToFolderRequest() {}
    
    public AddMealToFolderRequest(String mealId) {
        this.mealId = mealId;
    }
    
    public String getMealId() {
        return mealId;
    }
    
    public void setMealId(String mealId) {
        this.mealId = mealId;
    }
} 