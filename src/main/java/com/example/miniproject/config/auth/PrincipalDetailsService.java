package com.example.miniproject.config.auth;

import com.example.miniproject.entity.Member;
import com.example.miniproject.signup.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member loadUser = memberRepository.findByUsername(username).orElseThrow();
        return new PrincipalDetails(loadUser);
    }
}