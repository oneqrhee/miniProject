package com.example.miniproject.service;

import com.example.miniproject.dto.request.LoginRequestDto;
import com.example.miniproject.dto.request.MemberRequestDto;
import com.example.miniproject.dto.request.TokenDto;
import com.example.miniproject.dto.response.ResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.exception.ExceptionNamingHandler;
import com.example.miniproject.jwt.TokenProvider;
import com.example.miniproject.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional(readOnly = true)
    public boolean checkId(MemberRequestDto dto) {
        return memberRepository.findByUsername(dto.getUsername()).isEmpty();
    }

    @Transactional(readOnly = true)
    public boolean checkNick(MemberRequestDto dto) {
        return memberRepository.findByNickname(dto.getNickname()).isEmpty();
    }

    @Transactional
    public ResponseDto<String> signup(MemberRequestDto dto) {

        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 ID입니다");
        }
        if (memberRepository.findByNickname(dto.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다");
        }


        if (checkUsernameAndPassword(dto.getUsername(), dto.getPassword(), dto.getNickname())) {
            memberRepository.save(Member.builder()
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .nickname(dto.getNickname())
                    .createdAt(LocalDateTime.now())
                    .build());
            return new ResponseDto<>(HttpStatus.OK, dto.getNickname() + "님, 회원가입완료");

        }
        return new ResponseDto<>(HttpStatus.OK, "회원가입 실패");
    }

    private boolean findStr(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        return !m.find();
    }

    private boolean checkUsernameAndPassword(String username, String password, String nickname) {
        if (!(username.length() >= 4 && username.length() <= 12 && findStr("[^a-zA-Z0-9]", username)))
            // a~z, A~Z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.USERNAME_ERROR);

        if (!(password.length() >= 4 && password.length() <= 32 && findStr("[^a-z0-9]", password)))
            // a~z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.PASSWORD_ERROR);

        if (!(nickname.length() >= 2 && nickname.length() <= 8 && findStr("[^가-힣a-zA-Z0-9]", nickname)))
            // a~z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.NICKNAME_ERROR);

        return true;
    }

    @ExceptionHandler(value = Exception.class)
    public String proceedAllException(Exception e) {
        return String.format("<h1>%s</h1>", e.getMessage());
    }


    @Transactional
    public String login(LoginRequestDto dto, HttpServletResponse response) {
        Member member = isPresentMember(dto.getUsername());
        if (null == member) {
            return "MEMBER_NOT_FOUND";
        }
//        if (passwordEncoder.matches(member.getPassword(),dto.getPassword())) {
//            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
//        }

//        ResponseToken responseToken = new ResponseToken(member.getUsername());
//        return responseToken.getAccessToken();
//        String accessToken = responseToken.getAccessToken();
//        response.setHeader("Authorization","Bearer " + accessToken);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        tokenToHeaders(tokenDto, response);

        return member.getNickname();
    }

    @Transactional(readOnly = true)
    public Member isPresentMember(String username) {
        Optional<Member> optionalMember = memberRepository.findByUsername(username);
        return optionalMember.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
//        response.addHeader("Refresh-Token", "Bearer " + tokenDto.getRefreshToken());
//        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }
}



