package com.nomnom.backend.controller;

import com.nomnom.backend.dto.SimpleMealDto;
import com.nomnom.backend.exception.MealApiException;
import com.nomnom.backend.exception.MealNotFoundException;
import com.nomnom.backend.service.MealService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/meals")
@CrossOrigin(origins = "*")
public class MealController {
    
    private final MealService mealService;
    
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }
    
    @GetMapping("/ingredient/{ingredient}")
    public ResponseEntity<List<SimpleMealDto>> getMealsByIngredient(@PathVariable String ingredient) {
        List<SimpleMealDto> meals = mealService.getMealsByIngredient(ingredient);
        return ResponseEntity.ok(meals);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SimpleMealDto> getMealById(@PathVariable String id) {
        SimpleMealDto meal = mealService.getMealById(id);
        return ResponseEntity.ok(meal);
    }
    
    @GetMapping("/random")
    public ResponseEntity<SimpleMealDto> getRandomMeal() {
        SimpleMealDto meal = mealService.getRandomMeal();
        return ResponseEntity.ok(meal);
    }
    
    @ExceptionHandler(MealNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMealNotFoundException(MealNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Meal not found");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    
    @ExceptionHandler(MealApiException.class)
    public ResponseEntity<Map<String, Object>> handleMealApiException(MealApiException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "API Error");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
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