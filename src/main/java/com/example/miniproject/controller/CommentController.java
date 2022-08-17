package com.example.miniproject.controller;

import com.example.miniproject.dto.request.CommentRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comments/{productId}")
    public ResponseEntity<String> createComment(@PathVariable Long productId, @RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createComment(productId, requestDto, request);
    }

    @GetMapping("/api/comments/{id}")
    public CommentResponseDto getAllComments(@PathVariable Long id){
        return commentService.getAllComments(id);
    }

    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<String> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto,
                                                HttpServletRequest request ){
        return commentService.updateComment(commentId, requestDto, request);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, HttpServletRequest request){
         return commentService.deleteComment(commentId, request);
    }
}
