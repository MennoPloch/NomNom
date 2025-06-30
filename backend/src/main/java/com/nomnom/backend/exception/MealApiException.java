package com.nomnom.backend.exception;

public class MealApiException extends RuntimeException {
    
    public MealApiException(String message) {
        super(message);
    }
    
    public MealApiException(String message, Throwable cause) {
        super(message, cause);
    }
} 