package com.example.miniproject.controller;

import com.example.miniproject.securitytest.config.JwtTokenUtil;
import com.example.miniproject.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String getUsernameByRequest(HttpServletRequest request){
        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        return username;
    }

    @PostMapping("/api/likes/{id}")
    public void LikesProduct(@PathVariable Long id, HttpServletRequest request){
    likesService.likesProduct(id, getUsernameByRequest(request));
    }
}
