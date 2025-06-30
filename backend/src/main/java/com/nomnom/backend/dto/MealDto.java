package com.nomnom.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MealDto {
    
    @JsonProperty("idMeal")
    private String id;
    
    @JsonProperty("strMeal")
    private String name;
    
    @JsonProperty("strCategory")
    private String category;
    
    @JsonProperty("strArea")
    private String area;
    
    @JsonProperty("strInstructions")
    private String instructions;
    
    @JsonProperty("strMealThumb")
    private String thumbnail;
    
    @JsonProperty("strYoutube")
    private String youtubeUrl;
    
    @JsonProperty("strSource")
    private String sourceUrl;
    
    // Ingredients and measurements (up to 20)
    @JsonProperty("strIngredient1")
    private String ingredient1;
    @JsonProperty("strIngredient2")
    private String ingredient2;
    @JsonProperty("strIngredient3")
    private String ingredient3;
    @JsonProperty("strIngredient4")
    private String ingredient4;
    @JsonProperty("strIngredient5")
    private String ingredient5;
    @JsonProperty("strIngredient6")
    private String ingredient6;
    @JsonProperty("strIngredient7")
    private String ingredient7;
    @JsonProperty("strIngredient8")
    private String ingredient8;
    @JsonProperty("strIngredient9")
    private String ingredient9;
    @JsonProperty("strIngredient10")
    private String ingredient10;
    @JsonProperty("strIngredient11")
    private String ingredient11;
    @JsonProperty("strIngredient12")
    private String ingredient12;
    @JsonProperty("strIngredient13")
    private String ingredient13;
    @JsonProperty("strIngredient14")
    private String ingredient14;
    @JsonProperty("strIngredient15")
    private String ingredient15;
    @JsonProperty("strIngredient16")
    private String ingredient16;
    @JsonProperty("strIngredient17")
    private String ingredient17;
    @JsonProperty("strIngredient18")
    private String ingredient18;
    @JsonProperty("strIngredient19")
    private String ingredient19;
    @JsonProperty("strIngredient20")
    private String ingredient20;
    
    @JsonProperty("strMeasure1")
    private String measure1;
    @JsonProperty("strMeasure2")
    private String measure2;
    @JsonProperty("strMeasure3")
    private String measure3;
    @JsonProperty("strMeasure4")
    private String measure4;
    @JsonProperty("strMeasure5")
    private String measure5;
    @JsonProperty("strMeasure6")
    private String measure6;
    @JsonProperty("strMeasure7")
    private String measure7;
    @JsonProperty("strMeasure8")
    private String measure8;
    @JsonProperty("strMeasure9")
    private String measure9;
    @JsonProperty("strMeasure10")
    private String measure10;
    @JsonProperty("strMeasure11")
    private String measure11;
    @JsonProperty("strMeasure12")
    private String measure12;
    @JsonProperty("strMeasure13")
    private String measure13;
    @JsonProperty("strMeasure14")
    private String measure14;
    @JsonProperty("strMeasure15")
    private String measure15;
    @JsonProperty("strMeasure16")
    private String measure16;
    @JsonProperty("strMeasure17")
    private String measure17;
    @JsonProperty("strMeasure18")
    private String measure18;
    @JsonProperty("strMeasure19")
    private String measure19;
    @JsonProperty("strMeasure20")
    private String measure20;
    
    // Default constructor
    public MealDto() {}
    
    // Getters and Setters
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
    
    // Getters for ingredients
    public String getIngredient1() { return ingredient1; }
    public void setIngredient1(String ingredient1) { this.ingredient1 = ingredient1; }
    
    public String getIngredient2() { return ingredient2; }
    public void setIngredient2(String ingredient2) { this.ingredient2 = ingredient2; }
    
    public String getIngredient3() { return ingredient3; }
    public void setIngredient3(String ingredient3) { this.ingredient3 = ingredient3; }
    
    public String getIngredient4() { return ingredient4; }
    public void setIngredient4(String ingredient4) { this.ingredient4 = ingredient4; }
    
    public String getIngredient5() { return ingredient5; }
    public void setIngredient5(String ingredient5) { this.ingredient5 = ingredient5; }
    
    public String getIngredient6() { return ingredient6; }
    public void setIngredient6(String ingredient6) { this.ingredient6 = ingredient6; }
    
    public String getIngredient7() { return ingredient7; }
    public void setIngredient7(String ingredient7) { this.ingredient7 = ingredient7; }
    
    public String getIngredient8() { return ingredient8; }
    public void setIngredient8(String ingredient8) { this.ingredient8 = ingredient8; }
    
    public String getIngredient9() { return ingredient9; }
    public void setIngredient9(String ingredient9) { this.ingredient9 = ingredient9; }
    
    public String getIngredient10() { return ingredient10; }
    public void setIngredient10(String ingredient10) { this.ingredient10 = ingredient10; }
    
    public String getIngredient11() { return ingredient11; }
    public void setIngredient11(String ingredient11) { this.ingredient11 = ingredient11; }
    
    public String getIngredient12() { return ingredient12; }
    public void setIngredient12(String ingredient12) { this.ingredient12 = ingredient12; }
    
    public String getIngredient13() { return ingredient13; }
    public void setIngredient13(String ingredient13) { this.ingredient13 = ingredient13; }
    
    public String getIngredient14() { return ingredient14; }
    public void setIngredient14(String ingredient14) { this.ingredient14 = ingredient14; }
    
    public String getIngredient15() { return ingredient15; }
    public void setIngredient15(String ingredient15) { this.ingredient15 = ingredient15; }
    
    public String getIngredient16() { return ingredient16; }
    public void setIngredient16(String ingredient16) { this.ingredient16 = ingredient16; }
    
    public String getIngredient17() { return ingredient17; }
    public void setIngredient17(String ingredient17) { this.ingredient17 = ingredient17; }
    
    public String getIngredient18() { return ingredient18; }
    public void setIngredient18(String ingredient18) { this.ingredient18 = ingredient18; }
    
    public String getIngredient19() { return ingredient19; }
    public void setIngredient19(String ingredient19) { this.ingredient19 = ingredient19; }
    
    public String getIngredient20() { return ingredient20; }
    public void setIngredient20(String ingredient20) { this.ingredient20 = ingredient20; }
    
    // Getters for measures
    public String getMeasure1() { return measure1; }
    public void setMeasure1(String measure1) { this.measure1 = measure1; }
    
    public String getMeasure2() { return measure2; }
    public void setMeasure2(String measure2) { this.measure2 = measure2; }
    
    public String getMeasure3() { return measure3; }
    public void setMeasure3(String measure3) { this.measure3 = measure3; }
    
    public String getMeasure4() { return measure4; }
    public void setMeasure4(String measure4) { this.measure4 = measure4; }
    
    public String getMeasure5() { return measure5; }
    public void setMeasure5(String measure5) { this.measure5 = measure5; }
    
    public String getMeasure6() { return measure6; }
    public void setMeasure6(String measure6) { this.measure6 = measure6; }
    
    public String getMeasure7() { return measure7; }
    public void setMeasure7(String measure7) { this.measure7 = measure7; }
    
    public String getMeasure8() { return measure8; }
    public void setMeasure8(String measure8) { this.measure8 = measure8; }
    
    public String getMeasure9() { return measure9; }
    public void setMeasure9(String measure9) { this.measure9 = measure9; }
    
    public String getMeasure10() { return measure10; }
    public void setMeasure10(String measure10) { this.measure10 = measure10; }
    
    public String getMeasure11() { return measure11; }
    public void setMeasure11(String measure11) { this.measure11 = measure11; }
    
    public String getMeasure12() { return measure12; }
    public void setMeasure12(String measure12) { this.measure12 = measure12; }
    
    public String getMeasure13() { return measure13; }
    public void setMeasure13(String measure13) { this.measure13 = measure13; }
    
    public String getMeasure14() { return measure14; }
    public void setMeasure14(String measure14) { this.measure14 = measure14; }
    
    public String getMeasure15() { return measure15; }
    public void setMeasure15(String measure15) { this.measure15 = measure15; }
    
    public String getMeasure16() { return measure16; }
    public void setMeasure16(String measure16) { this.measure16 = measure16; }
    
    public String getMeasure17() { return measure17; }
    public void setMeasure17(String measure17) { this.measure17 = measure17; }
    
    public String getMeasure18() { return measure18; }
    public void setMeasure18(String measure18) { this.measure18 = measure18; }
    
    public String getMeasure19() { return measure19; }
    public void setMeasure19(String measure19) { this.measure19 = measure19; }
    
    public String getMeasure20() { return measure20; }
    public void setMeasure20(String measure20) { this.measure20 = measure20; }
} 