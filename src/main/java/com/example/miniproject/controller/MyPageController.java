package com.example.miniproject.controller;

import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.securitytest.config.JwtTokenUtil;
import com.example.miniproject.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final MemberRepository memberRepository;

    public Member getMember(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        return memberRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("검증되지 않은 회원입니다.")
        );
    }

    @GetMapping("/products") // 내가쓴글 조회하기
    public List<ProductsResponseDto> readMyProducts(HttpServletRequest request) {
        return myPageService.readMyProducts(getMember(request));
    }

    @GetMapping("/likes") //내가 좋아요한 게시글 조회하기
    public List<ProductsResponseDto> readMyProductsLikes(HttpServletRequest request) {
        return myPageService.readMyProductsLikes(getMember(request));
    }
}
