package com.example.miniproject.dto.response;

import com.example.miniproject.entity.Comment;
import com.example.miniproject.entity.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
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
