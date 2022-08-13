package com.example.miniproject.product;

import com.example.miniproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Post, Long> {
}
