package com.example.miniproject.controller;

import com.example.miniproject.dto.request.CommentRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comments/{postId}")
    public CommentResponseDto createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createComment(postId, requestDto, request);
    }

    @GetMapping("/api/comments/{id}")
    public CommentResponseDto getAllComments(@PathVariable Long id){
        return commentService.getAllComments(id);
    }

    @PutMapping("/api/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                            HttpServletRequest request ){
        return commentService.updateComment(commentId, requestDto, request);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId, HttpServletRequest request){
         commentService.deleteComment(commentId, request);
    }
}
