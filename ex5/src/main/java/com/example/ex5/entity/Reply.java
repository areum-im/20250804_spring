package com.example.ex5.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long rno;
  private String text;
  private String commenter;
  @ManyToOne
  private Board board; // 댓글이 어느 게시글 소속인지를 나타내는 핵심 연결 고리

}
