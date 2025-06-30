package com.nomnom.backend.dto;

import java.util.List;

public class SimpleMealDto {
    
    private String id;
    private String name;
    private String category;
    private String area;
    private String instructions;
    private String thumbnail;
    private String youtubeUrl;
    private String sourceUrl;
    private List<IngredientDto> ingredients;
    
    public SimpleMealDto() {}
    
    public SimpleMealDto(String id, String name, String category, String area, 
                        String instructions, String thumbnail, String youtubeUrl, 
                        String sourceUrl, List<IngredientDto> ingredients) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.thumbnail = thumbnail;
        this.youtubeUrl = youtubeUrl;
        this.sourceUrl = sourceUrl;
        this.ingredients = ingredients;
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
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getArea() {
        return area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    public String getInstructions() {
        return instructions;
    }
    
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    public String getThumbnail() {
        return thumbnail;
    }
    
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
    public String getYoutubeUrl() {
        return youtubeUrl;
    }
    
    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
    
    public String getSourceUrl() {
        return sourceUrl;
    }
    
    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }
    
    public List<IngredientDto> getIngredients() {
        return ingredients;
    }
    
    public void setIngredients(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }
} 