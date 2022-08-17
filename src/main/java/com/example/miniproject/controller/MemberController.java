package com.example.miniproject.controller;

import com.example.miniproject.dto.MemberDto;
import com.example.miniproject.dto.request.MemberRequestDto;
import com.example.miniproject.dto.response.ResponseDto;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    final MemberService memberService;

    private MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseDto<String> saveMember(@RequestBody MemberDto memberDto) {
        return memberService.saveMember(memberDto);
    }

    @PostMapping("/checkId")
    public ResponseDto<String> checkId(@RequestBody MemberRequestDto requestDto){
        if(memberRepository.findByUsername(requestDto.username).isPresent()){
            return new ResponseDto<>(HttpStatus.OK, "사용중인 Username입니다.");
        }
        return new ResponseDto<>(HttpStatus.OK, "사용 가능한 Username입니다.");

    }

    @PostMapping("/checkNick")
    public ResponseDto<String> checkNick(@RequestBody MemberRequestDto requestDto){
        if(memberRepository.findByUsername(requestDto.nickname).isPresent()){
            return new ResponseDto<>(HttpStatus.OK, "사용중인 Nickname입니다.");
        }
        return new ResponseDto<>(HttpStatus.OK, "사용 가능한 Nickname입니다.");

    }


}

