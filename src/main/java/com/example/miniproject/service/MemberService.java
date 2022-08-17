package com.example.miniproject.service;

import com.example.miniproject.dto.response.ResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.exception.ExceptionNamingHandler;
import com.example.miniproject.repository.MemberRepository;
import com.example.miniproject.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    final MemberRepository memberRepository;

    final PasswordEncoder encode;

        public ResponseDto<String> saveMember(MemberDto memberDto) {
            if (memberRepository.findByUsername(memberDto.getUsername()).isPresent()) {
                throw new IllegalArgumentException("이미 사용중인 ID입니다");
            }
            if (memberRepository.findByNickname(memberDto.getNickname()).isPresent()) {
                throw new IllegalArgumentException("이미 사용중인 닉네임입니다");
            }
            if (checkUsernameAndPassword(memberDto.getUsername(), memberDto.getPassword(), memberDto.getNickname())) {
                memberRepository.save(Member.createMember(
                        memberDto.getUsername(), encode.encode(memberDto.getPassword()), memberDto.getNickname()));
                return new ResponseDto<>(HttpStatus.OK, memberDto.getNickname() + "님, 회원가입완료");
            }

            return new ResponseDto<>(HttpStatus.OK, "회원가입 실패");

        }

    private boolean findStr(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        return m.find();
    }

    private boolean checkUsernameAndPassword(String username, String password, String nickname) {
        if (!(username.length() >= 4 && username.length() <= 12 && !findStr("[^a-zA-Z0-9]", username)))
            // a~z, A~Z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.USERNAME_ERROR);

        if (!(password.length() >= 4 && password.length() <= 32 && !findStr("[^a-z0-9]", password)))
            // a~z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.PASSWORD_ERROR);

        if (!(nickname.length() >= 2 && nickname.length() <= 8 && !findStr("[^가-힣a-zA-Z0-9]", nickname)))
            // a~z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.NICKNAME_ERROR);

        return true;
    }
}



