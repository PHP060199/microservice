package com.example.inventoryservice.exception;

public class AttributeExistsException extends RuntimeException {
    public AttributeExistsException(String message) {
        super((message));
    }
}
