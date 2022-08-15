package com.example.miniproject.controller;

import com.example.miniproject.config.jwt.token.RequestToken;
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

    private String getUsernameByRequest(HttpServletRequest request){
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }

    @PostMapping("/api/likes/{id}")
    public void likesPost(@PathVariable Long id, HttpServletRequest request){

        likesService.likesPost(id, getUsernameByRequest(request));
    }
}
