package com.example.ex4.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
public class GuestbookController {

  @GetMapping({"","/","list"})
  public String list() {
//   주소 복수개를 다 받을 수 있는 String을 사용해야 함
    log.info("guestbook/list................");
    return "/guestbook/list";
//    return "/guestbook";
//    return "/guestbook/";
  }

//  @PostMapping("/list")
//  public String postlist() {
//
//    return "/guestbook/list";
//  }
//  getmapping만 써도 구현됨
}
