package com.nomnom.backend.service;

import com.nomnom.backend.dto.*;
import com.nomnom.backend.exception.MealApiException;
import com.nomnom.backend.exception.MealNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {
    
    private final RestTemplate restTemplate;
    private final String baseUrl;
    
    public MealService(RestTemplate restTemplate, @Value("${mealdb.api.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }
    
    public List<SimpleMealDto> getMealsByIngredient(String ingredient) {
        try {
            String url = baseUrl + "/filter.php?i=" + ingredient;
            MealApiResponse response = restTemplate.getForObject(url, MealApiResponse.class);
            
            if (response == null || response.getMeals() == null || response.getMeals().isEmpty()) {
                throw new MealNotFoundException("No meals found for ingredient: " + ingredient);
            }
            
            return response.getMeals().stream()
                    .map(this::convertToSimpleMeal)
                    .collect(Collectors.toList());
                    
        } catch (HttpClientErrorException e) {
            throw new MealApiException("Error fetching meals by ingredient: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            throw new MealApiException("Unable to connect to TheMealDB API", e);
        }
    }
    
    public SimpleMealDto getMealById(String id) {
        try {
            String url = baseUrl + "/lookup.php?i=" + id;
            MealApiResponse response = restTemplate.getForObject(url, MealApiResponse.class);
            
            if (response == null || response.getMeals() == null || response.getMeals().isEmpty()) {
                throw new MealNotFoundException("Meal not found with id: " + id);
            }
            
            return convertToSimpleMeal(response.getMeals().get(0));
            
        } catch (HttpClientErrorException e) {
            throw new MealApiException("Error fetching meal by id: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            throw new MealApiException("Unable to connect to TheMealDB API", e);
        }
    }
    
    public SimpleMealDto getRandomMeal() {
        try {
            String url = baseUrl + "/random.php";
            MealApiResponse response = restTemplate.getForObject(url, MealApiResponse.class);
            
            if (response == null || response.getMeals() == null || response.getMeals().isEmpty()) {
                throw new MealApiException("No random meal available");
            }
            
            return convertToSimpleMeal(response.getMeals().get(0));
            
        } catch (HttpClientErrorException e) {
            throw new MealApiException("Error fetching random meal: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            throw new MealApiException("Unable to connect to TheMealDB API", e);
        }
    }
    
    private SimpleMealDto convertToSimpleMeal(MealDto meal) {
        List<IngredientDto> ingredients = extractIngredients(meal);
        
        return new SimpleMealDto(
                meal.getId(),
                meal.getName(),
                meal.getCategory(),
                meal.getArea(),
                meal.getInstructions(),
                meal.getThumbnail(),
                meal.getYoutubeUrl(),
                meal.getSourceUrl(),
                ingredients
        );
    }
    
    private List<IngredientDto> extractIngredients(MealDto meal) {
        List<IngredientDto> ingredients = new ArrayList<>();
        
        // Extract ingredients and measurements (up to 20)
        String[] ingredientArray = {
            meal.getIngredient1(), meal.getIngredient2(), meal.getIngredient3(), 
            meal.getIngredient4(), meal.getIngredient5(), meal.getIngredient6(),
            meal.getIngredient7(), meal.getIngredient8(), meal.getIngredient9(),
            meal.getIngredient10(), meal.getIngredient11(), meal.getIngredient12(),
            meal.getIngredient13(), meal.getIngredient14(), meal.getIngredient15(),
            meal.getIngredient16(), meal.getIngredient17(), meal.getIngredient18(),
            meal.getIngredient19(), meal.getIngredient20()
        };
        
        String[] measureArray = {
            meal.getMeasure1(), meal.getMeasure2(), meal.getMeasure3(),
            meal.getMeasure4(), meal.getMeasure5(), meal.getMeasure6(),
            meal.getMeasure7(), meal.getMeasure8(), meal.getMeasure9(),
            meal.getMeasure10(), meal.getMeasure11(), meal.getMeasure12(),
            meal.getMeasure13(), meal.getMeasure14(), meal.getMeasure15(),
            meal.getMeasure16(), meal.getMeasure17(), meal.getMeasure18(),
            meal.getMeasure19(), meal.getMeasure20()
        };
        
        for (int i = 0; i < ingredientArray.length; i++) {
            String ingredient = ingredientArray[i];
            String measure = measureArray[i];
            
            if (ingredient != null && !ingredient.trim().isEmpty()) {
                ingredients.add(new IngredientDto(
                    ingredient.trim(), 
                    measure != null ? measure.trim() : ""
                ));
            }
        }
        
        return ingredients;
    }
} 