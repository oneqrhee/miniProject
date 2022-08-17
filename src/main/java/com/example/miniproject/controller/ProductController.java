package com.example.miniproject.controller;


import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.security.config.jwt.token.RequestToken;
import com.example.miniproject.service.ProductService;
import lombok.RequiredArgsConstructor;
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


    @PostMapping("/product")
    public ResponseEntity<String> createProduct(@RequestPart MultipartFile multipartFile, @RequestPart ProductRequestDto productRequestDto,
                                        HttpServletRequest request) throws IOException {
        return productService.createProduct(multipartFile, productRequestDto, getUsernameByRequest(request));
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
    public ResponseEntity<String> updateProduct(@PathVariable Long productId,
                                                @RequestPart ProductRequestDto productRequestDto,
                                                @RequestPart MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        return productService.updateProduct(productId, productRequestDto, multipartFile, request);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId, HttpServletRequest request) {
        return productService.deleteProduct(productId, request);
    }

    private String getUsernameByRequest(HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }

}
