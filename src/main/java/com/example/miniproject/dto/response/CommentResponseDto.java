package com.example.miniproject.dto.response;

import com.example.miniproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
