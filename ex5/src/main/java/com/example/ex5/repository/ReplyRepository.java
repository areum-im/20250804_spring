package com.example.ex5.repository;

import com.example.ex5.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
  @Modifying
  @Query("delete from Reply r where r.board.bno=:bno ")
  void deleteByBno(@Param("bno") Long bno);

  // 쿼리메서드는 내부최적화가 되어 있으나 대량데이터일 경우 성능저하 발생
  // 쿼리메서드로 할 경우 Board의 bno를 가져와야 함으로 Board_Bno
//  void deleteByBoard_Bno(Long bno);

}
