package com.example.miniproject.service;

import com.example.miniproject.dto.request.MemberRequestDto;
import com.example.miniproject.dto.response.ResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.exception.ExceptionNamingHandler;
import com.example.miniproject.signup.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public ResponseDto<String> signup(MemberRequestDto dto) {

        Member member = dto.toMember();

        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 ID입니다");
        }
        if (checkUsernameAndPassword(member.getUsername(), member.getPassword())) {
            member.setPassword(passwordEncoder.encode(dto.getPassword()));
            memberRepository.save(member);
            return new ResponseDto<>(HttpStatus.OK, "회원가입완료");

        }

        return new ResponseDto<>(HttpStatus.OK, "회원가입 실패");
    }

    private boolean findStr(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    private boolean checkUsernameAndPassword(String username, String password) {
        if (!(username.length() >= 4 && username.length() <= 12 && !findStr("[^a-zA-Z0-9]", username))) // a~z, A~Z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.USERNAME_ERROR);

        if (!(password.length() >= 4 && password.length() <= 32 && !findStr("[^a-z0-9]", password))) // a~z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.PASSWORD_ERROR);

        return true;
    }
}


