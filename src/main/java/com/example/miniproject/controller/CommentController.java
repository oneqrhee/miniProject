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

    @PostMapping("/api/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest request){
        return commentService.createComment(requestDto, request);
    }

    @GetMapping("/api/comment/{id}")
    public CommentResponseDto getAllComments(@PathVariable Long id){
        return commentService.getAllComments(id);
    }

    @PutMapping("/api/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                            HttpServletRequest request ){
        return commentService.updateComment(id, requestDto, request);
    }

    @DeleteMapping("/api/comment/{id}")
    public void deleteComment(@PathVariable Long id, HttpServletRequest request){
         commentService.deleteComment(id, request);
    }
}
