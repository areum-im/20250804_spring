package com.example.ex5.repository;

import com.example.ex5.entity.Board;
import com.example.ex5.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardRepositoryTests {
  @Autowired
  private BoardRepository boardRepository;

  @Test
  public void insertBoard() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      Board board = Board.builder()
          .title("Title..." + i)
          .content("Content..." + i)
          .writer(Member.builder().email("user" + i + "@a.a").build())
          .build();
      boardRepository.save(board);
    });
  }

  // 불필요한 join 줄일려고 Fetchtype.Lazy 적용하고 Transactional 적용하면
  // insert, commit, rollback ... 일련의 과정들이 한번에 실행됨
  @Transactional
  @Test
  public void testRead1() {
    Optional<Board> result = boardRepository.findById(100l);
    Board board = result.get();
    System.out.println(board);
    System.out.println(board.getWriter());
  }

  @Test
  public void testReadWithWriter() {
    Object result = boardRepository.getBoardWithWriter(100L);
    Object[] arr = (Object[]) result;
    System.out.println(Arrays.toString(arr));
  }

  @Test
  public void testReadWithReply() {
    List<Object[]> result = boardRepository.getBoardWithReply(100l);
    for (Object[] arr : result) {
      System.out.println(Arrays.toString(arr));
    }
  }
}