package com.example.miniproject.repository;

import com.example.miniproject.entity.Comment;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByMember(Member member);
}
