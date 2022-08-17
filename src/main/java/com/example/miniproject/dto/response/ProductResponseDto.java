package com.example.miniproject.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ProductResponseDto {
    private Long id;
    private String title;
    private String nickname;
    private String imgUrl;
    private int size;
    private int price;
    private String content;
    private int likesCnt;
    private List<CommentResponseDto> commentList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
