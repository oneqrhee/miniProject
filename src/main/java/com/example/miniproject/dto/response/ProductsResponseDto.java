package com.example.miniproject.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class ProductsResponseDto {
    private String title;
    private String nickname;
    private String imgUrl;
    private int size;
    private int likesCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
