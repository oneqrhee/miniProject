package com.example.miniproject.repository;

import com.example.miniproject.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByNickname(String nickname);

}
