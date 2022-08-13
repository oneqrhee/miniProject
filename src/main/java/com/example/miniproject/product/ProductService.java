package com.example.miniproject.product;

import com.example.miniproject.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public void createProduct(ProductRequestDto productRequestDto) {
        Post post = Post.builder()
                .title(productRequestDto.getTitle())
                .size(productRequestDto.getSize())
                .price(productRequestDto.getPrice())
                .content(productRequestDto.getContent())
                .build();
        productRepository.save(post);

    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readAllPost() {
        List<Post> posts = productRepository.findAll();
        List<ProductsResponseDto> postList = new ArrayList<>();
        for (Post post : posts) {
            postList.add(ProductsResponseDto.builder()
                    .title(post.getTitle())
                    .size(post.getSize())
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build());
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto readPost(Long productId) {
        Post post = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return ProductResponseDto.builder()
                .title(post.getTitle())
                .size(post.getSize())
                .price(post.getPrice())
                .content(post.getContent())
                .commentList(post.getCommentList())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    @Transactional
    public void updateProduct(Long productId, ProductRequestDto productRequestDto){
        Post post = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        post.updatePost(productRequestDto);
    }

    @Transactional
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }
}
