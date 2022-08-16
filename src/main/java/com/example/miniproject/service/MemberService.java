package com.example.miniproject.service;

import com.example.miniproject.config.auth.PrincipalDetails;
import com.example.miniproject.config.auth.PrincipalDetailsService;
import com.example.miniproject.config.jwt.token.ResponseToken;
import com.example.miniproject.dto.request.LoginRequestDto;
import com.example.miniproject.dto.request.MemberRequestDto;
import com.example.miniproject.dto.response.ResponseDto;
import com.example.miniproject.entity.Member;
import com.example.miniproject.exception.ExceptionNamingHandler;
import com.example.miniproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MemberService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    private AuthenticationManager authenticationManager;

    private PrincipalDetailsService principalDetailsService;

    public ResponseDto<String> checkId(MemberRequestDto dto) {
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 ID입니다");

        }

        return new ResponseDto<>(HttpStatus.OK, "중복된 Id입니다");
    }

    @Transactional
    public ResponseDto<String> signup(MemberRequestDto dto) {

        if (memberRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 ID입니다");
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
        return m.find();
    }

    private boolean checkUsernameAndPassword(String username, String password, String nickname) {
        if (!(username.length() >= 4 && username.length() <= 12 && !findStr("[^a-zA-Z0-9]", username))) // a~z, A~Z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.USERNAME_ERROR);

        if (!(password.length() >= 4 && password.length() <= 32 && !findStr("[^a-z0-9]", password))) // a~z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.PASSWORD_ERROR);

        if (!(nickname.length() >= 2 && nickname.length() <= 8 && !findStr("[^가-힣a-zA-Z0-9]", nickname))) // a~z, 0~9 문자가 이외가 포함되면 false를 출력
            throw new IllegalArgumentException(ExceptionNamingHandler.NICKNAME_ERROR);

        return true;
    }

    @ExceptionHandler(value = Exception.class)
    public String proceedAllException(Exception e) {
        return String.format("<h1>%s</h1>", e.getMessage());
    }


    public ResponseDto<String> login(HttpServletResponse response, @RequestBody LoginRequestDto dto) {
        UserDetails principal = principalDetailsService.loadUserByUsername(dto.getUsername());
        String encoded = passwordEncoder.encode(dto.getPassword());
        Member member = memberRepository.findByUsername(dto.getUsername()).orElseThrow();
        if (!(Objects.equals(encoded, member.getPassword()))) {
            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
        }


        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                member.getUsername(), encoded);
        Authentication authentication = authenticationManager.authenticate(token);
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        ResponseToken responseToken = new ResponseToken(principalDetails);
        response.setHeader("Authorization","Bearer " + responseToken);

        return new ResponseDto<>(HttpStatus.OK,"로그인 완료");
    }


    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
}



