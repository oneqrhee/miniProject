package com.example.miniproject.signup;

import com.example.miniproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member , Long> {

    Optional<Member> findByUserId(String username);
}
