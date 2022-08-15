package com.example.miniproject.dto.response;


import com.example.miniproject.dto.request.MemberRequestDto;
import com.example.miniproject.entity.Member;
import lombok.Builder;
import java.time.LocalDateTime;


@Builder
public class MemberResponseDto{
    private String username;
    private String password;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


}


