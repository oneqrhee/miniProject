package com.example.miniproject.service;

import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.entity.Likes;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final ProductRepository productRepository;
    private final LikesRepository likesRepository;


    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readMyProducts(Member member) {

        List<Product> products = productRepository.findAllByMember(member);
        List<ProductsResponseDto> myProducts = new ArrayList<>();
        for (Product product : products) {
            myProducts.add(ProductsResponseDto.builder()
                    .title(product.getTitle())
                    .nickname(product.getMember().getNickname())
                    .imgUrl(product.getImgUrl())
                    .size(product.getSize())
                    .likesCnt(likesRepository.findAllByProduct(product).size())
                    .modifiedAt(product.getModifiedAt())
                    .createdAt(product.getCreatedAt())
                    .build());
        }
        return myProducts;
    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readMyProductsLikes(Member member) {

        List<Likes> likesList = likesRepository.findAllByMemberAndProductIsNotNull(member);
        List<Product> myProductsLikes = new ArrayList<>();
        for (Likes likes : likesList) {
            myProductsLikes.add(likes.getProduct());
        }
        List<ProductsResponseDto> myProductsLikesDtos = new ArrayList<>();
        for (Product product : myProductsLikes) {
            myProductsLikesDtos.add(ProductsResponseDto.builder()
                    .title(product.getTitle())
                    .nickname(product.getMember().getNickname())
                    .imgUrl(product.getImgUrl())
                    .size(product.getSize())
                    .likesCnt(likesRepository.findAllByProduct(product).size())
                    .modifiedAt(product.getModifiedAt())
                    .createdAt(product.getCreatedAt())
                    .build());
        }
        return myProductsLikesDtos;
    }
}
