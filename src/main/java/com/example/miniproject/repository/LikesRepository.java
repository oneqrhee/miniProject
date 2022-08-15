package com.example.miniproject.repository;

import com.example.miniproject.entity.Likes;
import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndProduct(Member member, Product product);
    List<Likes> findAllByProduct(Product product);
    List<Likes> findAllByMember(Member member);
}
