package com.example.miniproject.service;

import com.example.miniproject.entity.Likes;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    private final ProductRepository productRepository;

    @Transactional
    public void likesProduct(Long id, HttpServletRequest request) {

        //멤버 유효성 검사

        //포스트 id 검사
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Likes likes = isPresentLikes(member, product);
        if (null == likes){
            likesRepository.save(
                    Likes.builder()
//                            .member(member)
                            .product(product)
                            .build()
            );
        } else{
            likesRepository.delete(likes);
        }

    }

    @Transactional
    public Likes isPresentLikes(Member member, Product product){
        Optional<Likes> optionalLikes = likesRepository.findByMemberAndProduct(member, product);
        return optionalLikes.orElse(null);
    }
}
