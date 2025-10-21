package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JournalDTO {
  private Long jno;
  private String title;
  private String content;

  // Journal에 photos가 LAZY로 설정되어서
  // AllArgsConstructor하면 연결 안됨으로 기본 값으로 초기화
  @Builder.Default
  private List<PhotosDTO> photosDTOList = new ArrayList<>();

  private MembersDTO membersDTO;
  private Long likes;
  private Long commentsCnt;
  private LocalDateTime regDate;
  private LocalDateTime modDate;
}
