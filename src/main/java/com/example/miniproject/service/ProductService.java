package com.example.miniproject.service;

import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.entity.*;
import com.example.miniproject.repository.CommentRepository;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final CommentRepository commentRepository;

    private final LikesRepository likesRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void createProduct(ProductRequestDto productRequestDto , String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow();

        Product product = Product.builder()
                .title(productRequestDto.getTitle())
                .size(productRequestDto.getSize())
                .price(productRequestDto.getPrice())
                .content(productRequestDto.getContent())
                .nickname(member.getNickname())
                .member(member)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        productRepository.save(product);

    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readAllPost() {
        List<Product> products = productRepository.findAll();
        List<ProductsResponseDto> postList = new ArrayList<>();
        for (Product product : products) {
            postList.add(ProductsResponseDto.builder()
                    .title(product.getTitle())
                    .size(product.getSize())
                    .createdAt(product.getCreatedAt())
                    .modifiedAt(product.getModifiedAt())
                    .build());
        }
        return postList;
    }

    @Transactional(readOnly = true)
    public ProductResponseDto readPost(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        List<Comment> commentList = commentRepository.findAllByProduct(product);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList){
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .id(comment.getId())
                            .nickname(comment.getMember().getNickname())
                            .content(comment.getContent())
                            .createdAt(comment.getCreatedAt())
                            .modifiedAt(comment.getModifiedAt())
                            .build()
            );
        }

        return ProductResponseDto.builder()
                .title(product.getTitle())
                .size(product.getSize())
                .price(product.getPrice())
                .content(product.getContent())
                .commentList(commentResponseDtoList)
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();
    }

    @Transactional
    public void updateProduct(Long productId, ProductRequestDto productRequestDto){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        product.updateProduct(productRequestDto);
    }

    @Transactional
    public void deleteProduct(Long productId){
        productRepository.deleteById(productId);
    }


    // 좋아요 개수 나타내기
    @Transactional
    public int countLikes(Product product){
        List<Likes> likesList = likesRepository.findAllByProduct(product);
        return likesList.size();
    }

}
