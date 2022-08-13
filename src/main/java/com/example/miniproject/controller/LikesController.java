package com.example.miniproject.controller;

import com.example.miniproject.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/api/auth/likes/{id}")
    public void likesPost(@PathVariable Long id, HttpServletRequest request){
        likesService.likesPost(id, request);
    }
}