package com.example.miniproject.service;

import com.example.miniproject.dto.response.ProductsResponseDto;
import com.example.miniproject.entity.Likes;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import com.example.miniproject.jwt.TokenProvider;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.asynchttpclient.oauth.RequestToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final LikesRepository likesRepository;
    private final TokenProvider tokenProvider;

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readMyProducts(HttpServletRequest request) {
//        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기
//
//        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
//                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();
        Member member = validateMember(request);
        if (null == member) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
        }

        List<Product> products = productRepository.findAllByMember(member);
        List<ProductsResponseDto> myProducts = new ArrayList<>();
        for (Product product : products) {
            myProducts.add(ProductsResponseDto.builder()
                    .id(product.getId())
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
    public List<ProductsResponseDto> readMyProductsLikes(HttpServletRequest request) {
//        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기
//
//        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
//                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();
        Member member = validateMember(request);
        if (null == member) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
        }

        List<Likes> likesList = likesRepository.findAllByMemberAndProductIsNotNull(member);
        List<Product> myProductsLikes = new ArrayList<>();
        for (Likes likes : likesList) {
            myProductsLikes.add(likes.getProduct());
        }
        List<ProductsResponseDto> myProductsLikesDtos = new ArrayList<>();
        for (Product product : myProductsLikes) {
            myProductsLikesDtos.add(ProductsResponseDto.builder()
                    .id(product.getId())
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

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");
        if (!tokenProvider.validateToken(accessToken.substring(7))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}
