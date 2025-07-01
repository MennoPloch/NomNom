package com.nomnom.backend.controller;

import com.nomnom.backend.dto.SimpleMealDto;
import com.nomnom.backend.service.MealService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = MealController.class, excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class MealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MealService mealService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetMealsByIngredient() throws Exception {
        // Given
        SimpleMealDto meal1 = new SimpleMealDto();
        meal1.setId("1");
        meal1.setName("Chicken Curry");
        meal1.setThumbnail("http://example.com/chicken.jpg");

        SimpleMealDto meal2 = new SimpleMealDto();
        meal2.setId("2");
        meal2.setName("Chicken Tikka");
        meal2.setThumbnail("http://example.com/tikka.jpg");

        List<SimpleMealDto> meals = Arrays.asList(meal1, meal2);
        given(mealService.getMealsByIngredient("chicken")).willReturn(meals);

        // When & Then
        mockMvc.perform(get("/api/meals/ingredient/chicken")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].name").value("Chicken Curry"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].name").value("Chicken Tikka"));
    }

    @Test
    public void testGetMealById() throws Exception {
        // Given
        SimpleMealDto meal = new SimpleMealDto();
        meal.setId("52770");
        meal.setName("Spaghetti Bolognese");
        meal.setThumbnail("http://example.com/spaghetti.jpg");
        meal.setInstructions("Cook pasta, make sauce, combine.");

        given(mealService.getMealById("52770")).willReturn(meal);

        // When & Then
        mockMvc.perform(get("/api/meals/52770")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("52770"))
                .andExpect(jsonPath("$.name").value("Spaghetti Bolognese"))
                .andExpect(jsonPath("$.instructions").value("Cook pasta, make sauce, combine."));
    }

    @Test
    public void testGetRandomMeal() throws Exception {
        // Given
        SimpleMealDto randomMeal = new SimpleMealDto();
        randomMeal.setId("12345");
        randomMeal.setName("Random Dish");
        randomMeal.setThumbnail("http://example.com/random.jpg");

        given(mealService.getRandomMeal()).willReturn(randomMeal);

        // When & Then
        mockMvc.perform(get("/api/meals/random")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("12345"))
                .andExpect(jsonPath("$.name").value("Random Dish"));
    }
} 