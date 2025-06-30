package com.nomnom.backend.service;

import com.nomnom.backend.dto.SimpleMealDto;
import com.nomnom.backend.exception.MealNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "mealdb.api.url=https://www.themealdb.com/api/json/v1/1"
})
public class MealServiceTest {
    
    @Autowired
    private MealService mealService;
    
    @Test
    public void testGetRandomMeal() {
        SimpleMealDto meal = mealService.getRandomMeal();
        
        assertNotNull(meal);
        assertNotNull(meal.getId());
        assertNotNull(meal.getName());
        assertNotNull(meal.getThumbnail());
    }
    
    @Test
    public void testGetMealsByIngredient() {
        List<SimpleMealDto> meals = mealService.getMealsByIngredient("chicken");
        
        assertNotNull(meals);
        assertFalse(meals.isEmpty());
        
        SimpleMealDto firstMeal = meals.get(0);
        assertNotNull(firstMeal.getId());
        assertNotNull(firstMeal.getName());
    }
    
    @Test
    public void testGetMealById() {
        // Test with a known meal ID from TheMealDB
        SimpleMealDto meal = mealService.getMealById("52770");
        
        assertNotNull(meal);
        assertNotNull(meal.getId());
        assertNotNull(meal.getName());
        assertNotNull(meal.getInstructions());
    }
    
    @Test
    public void testGetMealByInvalidId() {
        assertThrows(MealNotFoundException.class, () -> {
            mealService.getMealById("999999");
        });
    }
    
    @Test
    public void testGetMealsByInvalidIngredient() {
        assertThrows(MealNotFoundException.class, () -> {
            mealService.getMealsByIngredient("invalidingredient123");
        });
    }
} 