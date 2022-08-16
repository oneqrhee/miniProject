package com.example.miniproject.controller;

import com.example.miniproject.dto.request.MemberRequestDto;
import com.example.miniproject.dto.response.ResponseDto;
import com.example.miniproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    @Autowired
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/api/member/signup")
    public ResponseDto<String> signup(@RequestBody MemberRequestDto requestDto){
        return memberService.signup(requestDto);
    }
}
