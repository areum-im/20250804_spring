package com.example.ex5.controller;


import com.example.ex5.dto.BoardDTO;
import com.example.ex5.dto.PageRequestDTO;
import com.example.ex5.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {
  private final BoardService boardService;

  // 목록
  @GetMapping({"","/","list"})
  public String list(PageRequestDTO pageRequestDTO, Model model) {
    log.info("board/list..." + pageRequestDTO);
    model.addAttribute("pageResultDTO", boardService.getList(pageRequestDTO));
    return "/board/list";
  }

//  등록 페이지로 이동
  @GetMapping("/register")
  public void register() { /* templates/board/register.html로 이동 */}

// 등록처리
@PostMapping("/register")
  public String registerPost(BoardDTO boardDTO, RedirectAttributes ra) {
    Long bno = boardService.register(boardDTO);
    log.info(">>" + bno +" 번 글이 등록되었습니다. ");
    // 모달, 알림용(일회성)
  ra.addFlashAttribute("msg", bno + "번 글이 등록되었습니다.");
  return "redirect:/board/list";
  }

//// 읽기 & 수정 화면 공용 로딩
//@GetMapping({"/read", "/modify"})
//public void read(Long bno, PageRequestDTO pageRequestDTO, Model model) {
//    log.info(">>bno:"+bno);
//    log.info(">>pageRequestDTO:" + pageRequestDTO);
//    BoardDTO boardDTO = boardService.get(bno);
//    toString(pageRequestDTO);
//}

}
