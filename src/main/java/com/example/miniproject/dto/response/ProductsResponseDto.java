package com.example.miniproject.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProductsResponseDto {
    private Long id;
    private String title;
    private String nickname;
    private String imgUrl;
    private int size;
    private int likesCnt;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
