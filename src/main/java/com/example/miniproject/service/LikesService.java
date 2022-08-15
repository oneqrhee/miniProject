package com.example.miniproject.service;

import com.example.miniproject.config.jwt.token.RequestToken;
import com.example.miniproject.entity.Likes;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Post;
import com.example.miniproject.repository.LikesRepository;
import com.example.miniproject.repository.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void likesPost(Long id, HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기
        String username = requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"));
        Member member = memberRepository.findByUsername(username).orElseThrow();
        //멤버 유효성 검사

        //포스트 id 검사
        Post post = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Likes likes = isPresentLikes(member, post);
        if (null == likes){
            likesRepository.save(
                    Likes.builder()
                            .member(member)
                            .post(post)
                            .build()
            );
        } else{
            likesRepository.delete(likes);
        }

    }

    @Transactional
    public Likes isPresentLikes(Member member, Post post){
        Optional<Likes> optionalLikes = likesRepository.findByMemberAndPost(member, post);
        return optionalLikes.orElse(null);
    }
}
