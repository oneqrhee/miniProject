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

    @PostMapping("/api/likes/{id}")
    public void likesProduct(@PathVariable Long id, HttpServletRequest request){
        likesService.likesProduct(id, request);
    }
}
