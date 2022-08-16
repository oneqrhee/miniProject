package com.example.miniproject.security.config.auth;

import com.example.miniproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrincipalDetails implements UserDetails{

    private Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){return null;}

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {  return member.getUsername(); }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
