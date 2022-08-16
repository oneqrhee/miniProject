package com.example.miniproject.service;

import com.example.miniproject.config.jwt.token.RequestToken;
import com.example.miniproject.dto.response.ProductsResponseDto;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final LikesRepository likesRepository;

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readMyPosts(HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기

        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();

        List<Post> posts = productRepository.findAllByMember(member);
        List<ProductsResponseDto> myPosts = new ArrayList<>();
        for (Post post : posts) {
            myPosts.add(ProductsResponseDto.builder()
                    .title(post.getTitle())
                    .nickname(post.getMember().getNickname())
                    .imgUrl(post.getImgUrl())
                    .size(post.getSize())
                    .likesCnt(likesRepository.findAllByPost(post).size())
                    .modifiedAt(post.getModifiedAt())
                    .createdAt(post.getCreatedAt())
                    .build());
        }
        return myPosts;
    }

    @Transactional(readOnly = true)
    public List<ProductsResponseDto> readMyPostsLikes(HttpServletRequest request) {
        RequestToken requestToken = new RequestToken(request); // servelet에서 토큰 가져오기

        Member member = memberRepository.findByUsername(requestToken.getUsername().orElseThrow(
                () -> new IllegalArgumentException("Can not find username"))).orElseThrow();

        List<Likes> likesList = likesRepository.findAllByMemberAndPostIsNotNull(member);
        List<Post> myPostsLikes = new ArrayList<>();
        for (Likes likes : likesList) {
            myPostsLikes.add(likes.getPost());
        }
        List<ProductsResponseDto> myPostLikesDtos = new ArrayList<>();
        for (Post post : myPostsLikes) {
            myPostLikesDtos.add(ProductsResponseDto.builder()
                    .title(post.getTitle())
                    .nickname(post.getMember().getNickname())
                    .imgUrl(post.getImgUrl())
                    .size(post.getSize())
                    .likesCnt(likesRepository.findAllByPost(post).size())
                    .modifiedAt(post.getModifiedAt())
                    .createdAt(post.getCreatedAt())
                    .build());
        }
        return myPostLikesDtos;
    }
}
