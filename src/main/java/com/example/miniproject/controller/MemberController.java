package com.example.miniproject.controller;

import com.example.miniproject.config.auth.PrincipalDetails;
import com.example.miniproject.config.jwt.token.ResponseToken;
import com.example.miniproject.dto.request.LoginRequestDto;
import com.example.miniproject.dto.request.MemberRequestDto;
import com.example.miniproject.dto.response.ResponseDto;
import com.example.miniproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private final MemberService memberService;

    private AuthenticationManager authenticationManager;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping("/signup")
    public ResponseDto<String> signup(@RequestBody MemberRequestDto requestDto){
        return memberService.signup(requestDto);
    }

    @PostMapping("/checkId")
    public ResponseDto<String> checkId(@RequestBody MemberRequestDto requestDto){
        return memberService.checkId(requestDto);
    }

    @PostMapping("/login")
    public ResponseDto<String> login(HttpServletResponse response, @RequestBody LoginRequestDto dto){
        String accessToken = memberService.login(dto);
        response.setHeader("Authorization","Bearer " + accessToken);

        return new ResponseDto<>(HttpStatus.OK,"로그인 성공");

    }

}
