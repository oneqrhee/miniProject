package com.example.miniproject.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ProductsResponseDto {
    private String title;
    private int size;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
