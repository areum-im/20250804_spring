package com.example.ex5.repository;

import com.example.ex5.entity.Board;
import com.example.ex5.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTests {
  @Autowired
  private BoardRepository boardRepository;

  @Test
  public void insertBoard() {
    IntStream.rangeClosed(1, 100).forEach(i->{
      Board board = Board.builder()
          .title("Title..." + i)
          .content("Content..." + i)
          .writer(Member.builder().email("user"+i+"@a.a").build())
          .build();
      boardRepository.save(board);
    });
  }
}