package com.example.miniproject.dto.response;


import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public class MemberResponseDto{
    private String username;
    private String password;
    private String nickname;
    private LocalDateTime createdAt;


}


