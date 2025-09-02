package com.example.ex7.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
@Log4j2
public class AuthController {

  @RequestMapping({"/login","/logout","/accessDenied","/authenticationFailure"})
  public void login(){}

}