package com.example.miniproject.service;

import com.example.miniproject.dto.request.ProductRequestDto;
import com.example.miniproject.dto.response.CommentResponseDto;
import com.example.miniproject.dto.response.ProductResponseDto;
import com.example.miniproject.entity.Post;
import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.ProductRepository;
import com.example.miniproject.s3Service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final S3Uploader s3Uploader;
    private final LikesRepository likesRepository;

    @Transactional
    public void createProduct(MultipartFile multipartFile, ProductRequestDto productRequestDto) throws IOException {
        String imgUrl = s3Uploader.upload(multipartFile, "upload");
        Post post = Post.builder()
                .title(productRequestDto.getTitle())
                .imgUrl(imgUrl)
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
                    .likesCnt(likesRepository.findAllByPost(post).size())
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
                .likesCnt(likesRepository.findAllByPost(post).size())
                .commentList(post.getCommentList().stream().map(CommentResponseDto::new).collect(Collectors.toList()))
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }

    @Transactional
    public void updateProduct(Long productId, ProductRequestDto productRequestDto) {
        Post post = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        post.updatePost(productRequestDto);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
