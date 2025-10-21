package com.example.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {
  private Long cno;
  private Long jno;
  private Long mid;
  private String nickname;
  private String email;
  private Long likes;
  private String text;
  private LocalDateTime regDate, modDate;
}
