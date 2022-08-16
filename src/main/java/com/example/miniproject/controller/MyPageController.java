package com.example.miniproject.controller;

import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/myPage")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    @GetMapping("/products") // 내가쓴글 조회하기
    public List<ProductsResponseDto> readMyProducts(HttpServletRequest request) {
        return myPageService.readMyProducts(request);
    }

    @GetMapping("/likes") //내가 좋아요한 게시글 조회하기
    public List<ProductsResponseDto> readMyProductsLikes(HttpServletRequest request) {
        return myPageService.readMyProductsLikes(request);
    }
}
