package com.example.miniproject.controller;

import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.service.ProductService;
import com.example.miniproject.dto.response.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/auth/products")
    public void createProduct(@RequestPart MultipartFile multipartFile, @RequestPart ProductRequestDto productRequestDto) throws IOException {
        productService.createProduct(multipartFile, productRequestDto);
    }

    @GetMapping("/products")
    public List<ProductsResponseDto> readAllPost(){
        return productService.readAllPost();
    }

    @GetMapping("/products/{productId}")
    public ProductResponseDto readPost(@PathVariable Long productId){
        return productService.readPost(productId);
    }

    @PutMapping("/auth/products/{productId}")
    public void updatePost(@PathVariable Long productId, @RequestBody ProductRequestDto productRequestDto){
        productService.updateProduct(productId, productRequestDto);
    }

    @DeleteMapping("/auth/products/{productId}")
    public void deletePost(@PathVariable Long productId){
        productService.deleteProduct(productId);
    }
}
