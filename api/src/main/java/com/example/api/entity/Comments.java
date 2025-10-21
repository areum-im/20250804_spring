package com.example.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = {"journal","members"})
public class Comments extends BasicEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cno;

  private Journal journal;
  @ManyToOne(fetch = FetchType.LAZY)
  private Members members;

  private Long Likes; // 별점
  private String text; // 한줄평

  public void changeLikes(Long likes) {
    this.Likes = likes;
  }
  public void changeText(String text) {
    this.text = text;
  }
}
