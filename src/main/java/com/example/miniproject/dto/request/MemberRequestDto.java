package com.example.miniproject.dto.request;

import com.example.miniproject.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberRequestDto {
    private String username;
    private String password;
    private String nickname;

    public Member toMember(){
        return Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
