package com.example.ex5.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  // IDENTITY : db의 auto_increment 사용, 새 글마다 bno 자동증가
  private Long bno;
  private String title;
  private String content;
  // 연관관계 : Board(많음) -> Member(1명) N:1
  // 한 회원이 여러 글을 쓸 수 있음
  // LAZY : Board만 먼저 가져오고, writer가 실제로 필요해지는 순간(ex: board.getWriter() 호출 시)
  // 그때 추가 쿼리로 불러옴 -> 불필요한 조인을 줄여 성능 최적화에 유리
  @ManyToOne(fetch = FetchType.LAZY)
  private Member writer;

  public void changeTitle(String title) {this.title = title;}
  public void changeContent(String content) {this.content = content;}
}
