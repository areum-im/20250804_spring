package com.example.ex8.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {
  private String[] pattern;
  private AntPathMatcher antPathMatcher;

  public ApiCheckFilter(String[] pattern) {
    this.pattern = pattern;
    antPathMatcher = new AntPathMatcher();
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    log.info("ApiCheckFilter.......................");
    log.info("Request URI: " + request.getRequestURI());

    boolean check = false;
    for (int i = 0; i < pattern.length; i++) {
      if (antPathMatcher.match(request.getContextPath() + pattern[i], request.getRequestURI())) {
        check = true; break;
      }
    }

    if(check) {
      if (checkAuthHeader(request)) {
        filterChain.doFilter(request, response);
        return;
      } else {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        String message = "FAIL CHECK API TOKEN";
        json.put("code", "403");
        json.put("message", message);
        PrintWriter out = response.getWriter();
        out.println(json);
        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private boolean checkAuthHeader(HttpServletRequest request) {
    boolean checkResult = false;
    String authHeader = request.getHeader("Authorization");

    if (StringUtils.hasText(authHeader)) {
      log.info("Authorization existed: " + authHeader);
      if (authHeader.equals("12345678")) {
        checkResult = true;
      }
    }
    return checkResult;
  }
}
