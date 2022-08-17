//package com.example.miniproject.security.config.jwt.token;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.miniproject.entity.Member;
//import com.example.miniproject.security.config.auth.PrincipalDetails;
//import com.example.miniproject.security.config.jwt.token.properties.AccessTokenProperties;
//import com.example.miniproject.security.config.jwt.token.properties.CommonTokenProperties;
//import lombok.Getter;
//
//import java.util.Date;
//
//
//@Getter
//public class ResponseToken {
//    private String accessToken;
//    private PrincipalDetails principalDetails;
//
//    public ResponseToken(PrincipalDetails principalDetails) {
//        this.principalDetails = principalDetails;
//        accessToken = makeToken("accessToken", AccessTokenProperties.EXPIRE_TIME);
//    }
//
//    public ResponseToken(String name) {
//        Member member = Member.builder().username(name).build();
//        this.principalDetails = new PrincipalDetails(member);
//        accessToken = makeToken("accessToken", AccessTokenProperties.EXPIRE_TIME);
//    }
//
//
//    public String makeToken(String tokenName, int expire) {
//        return JWT.create()
//                .withSubject(tokenName)
//                .withExpiresAt(new Date(System.currentTimeMillis() + expire))
//                .withClaim("username", principalDetails.getMember().getUsername())
//                .sign(Algorithm.HMAC512(CommonTokenProperties.SECRET));
//    }
//}
