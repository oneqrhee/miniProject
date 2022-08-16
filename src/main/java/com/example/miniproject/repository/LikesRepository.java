package com.example.miniproject.repository;

import com.example.miniproject.entity.Likes;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndPost(Member member, Post post);
    List<Likes> findAllByPost(Post post);
    List<Likes> findAllByMember(Member member);
    List<Likes> findAllByMemberAndPostIsNotNull(Member member);
}
