//package com.example.miniproject.security.config.jwt;
//
//import com.example.miniproject.entity.Member;
//import com.example.miniproject.repository.MemberRepository;
//import com.example.miniproject.security.config.auth.PrincipalDetails;
//import com.example.miniproject.security.config.jwt.token.RequestToken;
//import com.example.miniproject.security.config.jwt.token.ResponseToken;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    private AuthenticationManager authenticationManager;
//
//    private RequestToken requestToken;
//
//    private MemberRepository repository;
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//        this.requestToken = requestToken;
//
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        this.requestToken = new RequestToken(request);
//        String token = requestToken.getAccessToken();
//        String username = requestToken.getUsername().orElseThrow();
//        repository.findByUsername(username).orElseThrow();
//
//
//
//        return new Authentication().isAuth;
//    }
//
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
//        ResponseToken responseToken = new ResponseToken(principalDetails);
//        String accessToken = responseToken.getAccessToken();
//
//        response.setHeader("Authorization", "Bearer " + accessToken);
//
//    }
//
//
//}
