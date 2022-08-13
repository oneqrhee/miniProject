package com.example.miniproject.config.jwt;

import com.example.miniproject.config.auth.PrincipalDetails;
import com.example.miniproject.config.jwt.token.ResponseToken;
import com.example.miniproject.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Member member = objectMapper.readValue(request.getInputStream(),Member.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member.getUsername(),member.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);

            return authentication;

        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        ResponseToken responseToken = new ResponseToken(principalDetails);
        String accessToken = responseToken.getAccessToken();

        response.setHeader("Authorization","Bearer " + accessToken);

    }


}
