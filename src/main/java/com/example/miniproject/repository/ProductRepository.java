package com.example.miniproject.repository;

import com.example.miniproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Post, Long> {
}
