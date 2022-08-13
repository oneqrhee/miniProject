package com.example.miniproject.dto.response;

import com.example.miniproject.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class ProductResponseDto {
    private String title;
    private int size;
    private int price;
    private String content;
    private List<Comment> commentList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
