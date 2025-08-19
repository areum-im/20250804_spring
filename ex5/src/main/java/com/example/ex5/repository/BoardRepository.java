package com.example.ex5.repository;

import com.example.ex5.entity.Board;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

  // @Query() : jpql 활용해 조인하는 법을 보여주고 있음
  // 연관관계가 있는 경우 :: board 기준 board가 member를 참조하기 때문에 left join을 적용
  @Query("select b, w from Board b left join b.writer w where b.bno=:bno ") // b.writer : member를 w라고 해줌
  // board, writer 두개 다 받기 위해 Object 사용
  Object getBoardWithWriter(Long bno); // @Param() : 예를 들어 bno 말고 다른 이름 사용하고 싶으면 붙이기

  // 연관관계가 없는 경우 :: board 기준 reply가 board를 참조하기 때문에 left join ON을 적용
  @Query("select b, r from Board b left join Reply r on r.board = b where b.bno=:bno ")
  List<Object[]> getBoardWithReply(Long bno);
}
