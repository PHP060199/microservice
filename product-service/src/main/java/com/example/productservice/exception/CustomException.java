package com.example.productservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException implements Supplier<RuntimeException> {
    private final String message;
    private final int code;

    @Override
    public RuntimeException get() {
        return this;
    }
}
