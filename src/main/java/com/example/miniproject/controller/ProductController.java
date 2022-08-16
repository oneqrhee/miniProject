package com.example.miniproject.controller;

import com.example.miniproject.config.jwt.token.RequestToken;
import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.service.ProductService;
import com.example.miniproject.dto.response.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;


    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestPart MultipartFile multipartFile, @RequestPart ProductRequestDto productRequestDto,
                                        HttpServletRequest request) throws IOException {
        return productService.createProduct(multipartFile, productRequestDto, getUsernameByRequest(request));
    }

    @GetMapping("/products")
    public List<ProductsResponseDto> readAllPost() {
        return productService.readAllPost();
    }

    @GetMapping("/product/{productId}")
    public ProductResponseDto readPost(@PathVariable Long productId) {
        return productService.readPost(productId);
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<String> updatePost(@PathVariable Long productId, @RequestBody ProductRequestDto productRequestDto, HttpServletRequest request) {
        return productService.updateProduct(productId, productRequestDto, request);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deletePost(@PathVariable Long productId, HttpServletRequest request) {
        return productService.deleteProduct(productId, request);
    }

    private String getUsernameByRequest(HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }

}
