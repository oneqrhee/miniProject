package com.example.miniproject.service;

import com.example.miniproject.config.jwt.token.RequestToken;
import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Post;
import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void createProduct(ProductRequestDto productRequestDto , HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기
        String username = requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"));
        Member member = memberRepository.findByUsername(username).orElseThrow();



        Post post = Post.builder()
                .title(productRequestDto.getTitle())
                .size(productRequestDto.getSize())
                .price(productRequestDto.getPrice())
                .content(productRequestDto.getContent())
                .nickname(member.getNickname())
                .member(member)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
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
