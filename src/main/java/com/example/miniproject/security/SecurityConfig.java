package com.example.miniproject.security;

import com.example.miniproject.security.config.jwt.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Autowired
    private CorsConfig config;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        return (web) -> web.ignoring()
                .antMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable() //Csrf 토큰요청 기능 OFF( JWT나 OAuth2 사용시 불필요)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        ;

        http
                .authorizeRequests()
                .antMatchers("/", "/**").permitAll()
                .antMatchers("/api/member/**", "/api/products/**").permitAll()
                .anyRequest().authenticated();


        return http.build();
    }

    public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.authorizeRequests()
                    .antMatchers("/", "/**").permitAll()
                    .antMatchers("/api/member/**", "/api/products/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(config.corsFilter())
                    .addFilter(new JwtAuthorizationFilter(authenticationManager));


//                    .authorizeRequests()
//                    .antMatchers("/api/member/**", "/api/products/**").permitAll()
//                    .anyRequest().authenticated();


        }


    }
}


