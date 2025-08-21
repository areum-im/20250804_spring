package com.example.ex5.service;

import com.example.ex5.dto.*;
import com.example.ex5.dto.ReplyDTO;
import com.example.ex5.entity.*;
import com.example.ex5.entity.Reply;
import com.example.ex5.entity.Reply;

import java.util.List;

public interface ReplyService {
  // 댓글 등록
  Long register(ReplyDTO replyDTO);

  // 게시글(bno) 기준 댓글 목록 조회
  List<ReplyDTO> getList(Long rno);

  // 댓글 수정
  void modify(ReplyDTO replyDTO);

  // 댓글 삭제
  void remove(Long rno);

  default Reply dtoToEntity(ReplyDTO replyDTO) {
    Reply reply = Reply.builder()
        .rno(replyDTO.getRno())  // 신규 등록 시 null이어도 OK (JPA가 생성)
        .text(replyDTO.getText())
        .commenter(replyDTO.getCommenter())  // 엔티티가 String commenter일 때
        .board(Board.builder()
            .bno(replyDTO.getBno())
            .build())  // 연관관계 주입(프록시 참조로 충분)
        .build();
    return reply;
  }

  default ReplyDTO entityToDto(Reply reply) {
    return ReplyDTO.builder()
        .rno(reply.getRno())
        .text(reply.getText())
        .commenter(reply.getCommenter())
        .bno(reply.getBoard().getBno())
        .regDate(reply.getRegDate())
        .modDate(reply.getModDate())
        .build();
  }

}
