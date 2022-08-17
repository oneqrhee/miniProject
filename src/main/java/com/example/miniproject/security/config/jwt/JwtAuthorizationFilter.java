//package com.example.miniproject.security.config.jwt;
//
//import com.example.miniproject.repository.MemberRepository;
//import com.example.miniproject.security.config.jwt.token.RequestToken;
//import com.example.miniproject.security.config.jwt.token.properties.AccessTokenProperties;
//import com.example.miniproject.security.config.jwt.token.properties.CommonTokenProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
//
//    private MemberRepository memberRepository;
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
//        super(authenticationManager, authenticationEntryPoint);
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//
//        String jwtHeader = request.getHeader(AccessTokenProperties.HEADER_STRING);
//        String bearerToken = jwtHeader.substring("Bearer ".length());
//
//        // JWT Token 을 검증하여 정상적인 사용자인지 확인해봐야함
//        if (bearerToken == null || !bearerToken.startsWith(CommonTokenProperties.TOKEN_PREFIX)) { // 헤더가 없거나 Bearer 이 아닌 경우
//            chain.doFilter(request, response);
//            return;
//        }
//
//
//
//
//        chain.doFilter(request, response);
//
//
//    }
//}
//
