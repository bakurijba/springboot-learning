package com.spring.learning.dto.shared;

import lombok.Data;

@Data
public class ApiResponseDto {
    private String message;
    private boolean success;

    public ApiResponseDto(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
