package com.example.miniproject.controller;

import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.service.ProductService;
import com.example.miniproject.dto.response.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/auth/products")
    public void createProduct(@RequestBody ProductRequestDto productRequestDto){
        productService.createProduct(productRequestDto);
    }

    @GetMapping("/products")
    public List<ProductsResponseDto> readAllProduct(){
        return productService.readAllPost();
    }

    @GetMapping("/products/{productId}")
    public ProductResponseDto readProduct(@PathVariable Long productId){
        return productService.readPost(productId);
    }

    @PutMapping("/auth/products/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDto productRequestDto){
        productService.updateProduct(productId, productRequestDto);
    }

    @DeleteMapping("/auth/products/{productId}")
    public void deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
    }
}
