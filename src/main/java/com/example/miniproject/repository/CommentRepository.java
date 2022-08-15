package com.example.miniproject.repository;

import com.example.miniproject.entity.Comment;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProduct(Product product);
    List<Comment> findAllByMember(Member member);
}
