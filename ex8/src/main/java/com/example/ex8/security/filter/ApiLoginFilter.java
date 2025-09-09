package com.example.ex8.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

@Log4j2
public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {
  public ApiLoginFilter(String defaultFilterProcessesUrl) {
    super(defaultFilterProcessesUrl);
  }


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
      throws AuthenticationException, IOException, ServletException {

    log.info("ApiLoginFilter..............attemptAuthentication");
    String email = request.getParameter("email");
    String pass = "1";
    if(email == null) throw new BadCredentialsException("Email cannot be null");

    // ClubUserDetailsService의 loadUserByUsername()를 호출하고 인증
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(email, pass);
    return getAuthenticationManager().authenticate(authToken);

  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
  }
}
