package com.example.miniproject.controller;


import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.securitytest.config.JwtTokenUtil;
import com.example.miniproject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

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


    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestPart MultipartFile multipartFile, @RequestPart ProductRequestDto productRequestDto,
                                        HttpServletRequest request) throws IOException {
        return productService.createProduct(multipartFile, productRequestDto, getMember(request));
    }

    @GetMapping("/products")
    public List<ProductsResponseDto> readAllProducts() {
        return productService.readAllProducts();
    }

    @GetMapping("/product/{productId}")
    public ProductResponseDto readProduct(@PathVariable Long productId) {
        return productService.readProduct(productId);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto productRequestDto, HttpServletRequest request) {
        return productService.updateProduct(productId, productRequestDto, getMember(request));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId, HttpServletRequest request) {
        return productService.deleteProduct(productId, getMember(request));
    }




}
