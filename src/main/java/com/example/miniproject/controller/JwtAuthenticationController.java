package com.example.miniproject.controller;

import com.example.miniproject.securitytest.config.JwtTokenUtil;
import com.example.miniproject.entity.Member;
import com.example.miniproject.securitytest.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
@RequestMapping("/api/member")
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response) throws Exception {
        final Member member = userDetailService.authenticateByEmailAndPassword
                (authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final String token = jwtTokenUtil.generateToken(member.getUsername());
        response.setHeader("Authorization","Bearer "+token);
        return ResponseEntity.ok(new JwtResponse("Bearer "+token));
    }

}

@Data
class JwtRequest {

    private String username;
    private String password;

}

@Data
@AllArgsConstructor
class JwtResponse {

    private String token;

}

