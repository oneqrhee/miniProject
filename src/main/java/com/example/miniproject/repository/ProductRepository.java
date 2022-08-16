package com.example.miniproject.repository;

import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByMember(Member member);
}
