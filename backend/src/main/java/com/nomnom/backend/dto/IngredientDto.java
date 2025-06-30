package com.nomnom.backend.dto;

public class IngredientDto {
    
    private String name;
    private String measure;
    
    public IngredientDto() {}
    
    public IngredientDto(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMeasure() {
        return measure;
    }
    
    public void setMeasure(String measure) {
        this.measure = measure;
    }
} 