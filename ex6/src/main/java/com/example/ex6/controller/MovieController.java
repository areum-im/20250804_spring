package com.example.ex6.controller;

import com.example.ex6.dto.MovieDTO;
import com.example.ex6.dto.PageRequestDTO;
import com.example.ex6.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {
  private final MovieService movieService;

  @GetMapping("register")
  public void register() {
  }

  @PostMapping("/register")
  public String register(MovieDTO movieDTO, RedirectAttributes ra) {
    Long mno = movieService.register(movieDTO);
    ra.addFlashAttribute("msg", mno);
    return "redirect:/movie/list";
  }

  @GetMapping({"", "/", "/list"})
  public String list(PageRequestDTO pageRequestDTO, Model model) {
    model.addAttribute("pageResultDTO", movieService.getList(pageRequestDTO));
    return "/movie/list";
  }

  @GetMapping({"/read", "/modify"}) // modify 화면도 같은 DTO를 재사용할 수 있도록 묶어둡니다(선택).
  public void read(Long mno, PageRequestDTO pageRequestDTO, Model model) {
    model.addAttribute("movieDTO", movieService.get(mno));
    log.info("READ mno={}, pageReq={}", mno, pageRequestDTO);

    MovieDTO movieDTO = movieService.get(mno); // 상세 조회 서비스 호출 (아래 2번 참고)
    // view 이름은 요청 경로에 따라 /movie/read.html 또는 /movie/modify.html 를 자동으로 찾습니다.
  }

  @GetMapping("/modify")
  public void modify(Long mno, PageRequestDTO pageRequestDTO, Model model) {
    model.addAttribute("movieDTO", movieService.get(mno));

  }

  @PostMapping("/modify")
  public String modify(MovieDTO movieDTO, PageRequestDTO pageRequestDTO,
                       RedirectAttributes ra) {
    movieService.modify(movieDTO);  // 수정 처리
    ra.addAttribute("page", pageRequestDTO.getPage());
    return "redirect:/movie/list";   // 목록으로 이동
  }
}
