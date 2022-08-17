package com.example.miniproject.dto.request;

public class MemberRequestDto {

    public String username;

    public String nickname;


    public MemberRequestDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
