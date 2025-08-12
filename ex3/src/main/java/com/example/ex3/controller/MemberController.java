package com.example.ex3.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {


  @RequestMapping("/join")
  public String join() {

    return "/member/join";
  }

  @GetMapping("/list")
  public String getlist() {

    return "/member/list";
  }

  @PostMapping("/list")
  public String postlist() {

    return "/member/list";
  }
}
