package com.nomnom.backend.exception;

public class MealNotFoundException extends RuntimeException {
    
    public MealNotFoundException(String message) {
        super(message);
    }
    
    public MealNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 