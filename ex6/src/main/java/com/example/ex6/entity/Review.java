package com.example.ex6.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
// JPA의 연관관계가 양방향이거나 Lazy 로딩이 걸려있으면,
// toString() 호출 시 불필요한 SQL 발생 가능
// 따라서 연관 관계 필드는 toString에서 제외하는 게 안전함
@ToString(exclude = {"movie", "member"})
public class Review extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long reviewNum;
  @ManyToOne(fetch = FetchType.LAZY)
  private Movie movie; // 어떤 영화에 대한 리뷰인지 연결
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member; // 어떤 회원이 쓴 리뷰인지 연결
  private int grade; // 평점
  private String text; // 리뷰 내용

  // 작성이유 : 유지보수가 쉬움
  public void changeGrade(int grade) {
    this.grade = grade;
  }
  // 서비스계층에서 이런 식으로 grade 변경 시
  // 비즈니스 룰을 changeGrade()안에 넣을 수 있음
  //
  public void changeText(String text) {
    this.text = text;
  }
}

