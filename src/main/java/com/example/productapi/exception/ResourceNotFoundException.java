package com.example.productapi.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(final String message) { 
        super(message); 
    }
}