package com.example.api.config;

import com.example.api.security.filter.ApiCheckFilter;
import com.example.api.security.filter.ApiLoginFilter;
import com.example.api.security.handler.ApiLoginFailHandler;
import com.example.api.security.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
  private static final String[] AUTH_WHITELIST = {
      "/"
      , "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html"
      , "/members/register"
  };

  private final String[] AUTH_CHECKLIST = {
      "/comments/**", "/journal/**", "/members/get/**", "/uploadAjax", "/removeFile/**"
      , "/display/**"
  };

  // 주소 checking 위해 사용
  @Bean
  protected SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
    httpSecurity.authorizeHttpRequests(auth ->
        auth.
            // 1) 개발 시 모든 주소 허용 :: 단독 사용
            anyRequest().permitAll()

            // 2) 회원가입 등은 인증 상관 없이 수용함으로 , 나중에 CORS 적용하여 처리
            // auth.requestMatchers(AUTH_WHITELIST).permitAll()

            // 3) 조건부 수용 :: 주소는 열어줬지만, 토큰으로 체큰
            .requestMatchers("/members/get/**").permitAll()

            // 4) 그 외는 막음
            // .anyRequest().denyAll()
    );
    httpSecurity.addFilterBefore(
        // apiCheckFilter : token 유무 확인하는 곳
        apiCheckFilter(AUTH_CHECKLIST),
        UsernamePasswordAuthenticationFilter.class
    );
    httpSecurity.addFilterBefore(
        apiLoginFilter(httpSecurity.getSharedObject(AuthenticationConfiguration.class)),
        UsernamePasswordAuthenticationFilter.class
    );
    return httpSecurity.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ApiCheckFilter apiCheckFilter() {
    return new ApiCheckFilter(AUTH_CHECKLIST, jwtUtil());
  }

  @Bean
  public ApiLoginFilter apiLoginFilter(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login", jwtUtil());
    apiLoginFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
    apiLoginFilter.setAuthenticationFailureHandler(getApiLoginFailHandler());
    return apiLoginFilter;
  }

  @Bean
  public JWTUtil jwtUtil() {
    return new JWTUtil();
  }

  @Bean
  public ApiLoginFailHandler getApiLoginFailHandler() {
    return new ApiLoginFailHandler();
  }


}