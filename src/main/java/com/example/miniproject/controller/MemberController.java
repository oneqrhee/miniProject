package com.example.miniproject.controller;

import com.example.miniproject.dto.request.LoginRequestDto;
import com.example.miniproject.dto.request.MemberRequestDto;
import com.example.miniproject.dto.response.ResponseDto;
import com.example.miniproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public boolean checkId(@RequestBody MemberRequestDto requestDto){
        return memberService.checkId(requestDto);
    }

    @PostMapping("/checkNickname")
    public boolean checkNick(@RequestBody MemberRequestDto requestDto){
        return memberService.checkNick(requestDto);
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response, @RequestBody LoginRequestDto dto){
//        String accessToken = memberService.login(dto);
//        response.setHeader("Authorization","Bearer " + accessToken);

        return memberService.login(dto, response);
    }
}
