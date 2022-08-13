package com.example.miniproject.service;

import com.example.miniproject.entity.Likes;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Post;
import com.example.miniproject.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void likesPost(Long id, HttpServletRequest request) {

        //멤버 유효성 검사

        //포스트 id 검사

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
