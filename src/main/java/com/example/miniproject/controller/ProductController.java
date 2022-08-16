package com.example.miniproject.controller;

import com.example.miniproject.config.jwt.token.RequestToken;
import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.service.ProductService;
import com.example.miniproject.dto.response.ProductsResponseDto;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/products")
    public void createProduct(@RequestPart MultipartFile multipartFile, @RequestPart ProductRequestDto productRequestDto,
                              HttpServletRequest request) throws IOException {
        productService.createProduct(multipartFile, productRequestDto, getUsernameByRequest(request));
    }

    @GetMapping("/products")
    public List<ProductsResponseDto> readAllPost() {
        return productService.readAllPost();
    }

    @GetMapping("/products/{productId}")
    public ProductResponseDto readPost(@PathVariable Long productId) {
        return productService.readPost(productId);
    }

    @PutMapping("/products/{productId}")
    public void updatePost(@PathVariable Long productId, @RequestBody ProductRequestDto productRequestDto) {
        productService.updateProduct(productId, productRequestDto);
    }

    @DeleteMapping("/products/{productId}")
    public void deletePost(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }


    private String getUsernameByRequest(HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request);
        return requestToken.getUsername().orElseThrow();
    }

}
