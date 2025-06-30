package com.nomnom.backend.dto;

import java.util.List;

public class MealApiResponse {
    
    private List<MealDto> meals;
    
    public MealApiResponse() {}
    
    public List<MealDto> getMeals() {
        return meals;
    }
    
    public void setMeals(List<MealDto> meals) {
        this.meals = meals;
    }
} 