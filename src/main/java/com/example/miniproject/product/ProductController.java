package com.example.miniproject.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {
    private final ProductService postService;

    @PostMapping("/products")
    public void createProduct(ProductRequestDto productRequestDto){
        postService.createProduct(productRequestDto);
    }
}
