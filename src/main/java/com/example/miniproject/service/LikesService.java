package com.example.miniproject.service;

import com.example.miniproject.entity.Likes;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import com.example.miniproject.jwt.TokenProvider;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public boolean likesProduct(Long id, HttpServletRequest request) {

//        Member member = memberRepository.findByUsername(username).orElseThrow();
        Member member = validateMember(request);
        if (null == member) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "INVALID_TOKEN");
        }
        //멤버 유효성 검사

        //포스트 id 검사
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Likes likes = isPresentLikes(member, product);
        if (null == likes) {
            likesRepository.save(
                    Likes.builder()
                            .member(member)
                            .product(product)
                            .build()
            );
            return true;
        } else {
            likesRepository.delete(likes);
            return false;
        }
    }

    @Transactional
    public Likes isPresentLikes(Member member, Product product) {
        Optional<Likes> optionalLikes = likesRepository.findByMemberAndProduct(member, product);
        return optionalLikes.orElse(null);
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
