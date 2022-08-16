package com.example.miniproject.repository;

import com.example.miniproject.entity.Member;
import com.example.miniproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByMember(Member member);
}
