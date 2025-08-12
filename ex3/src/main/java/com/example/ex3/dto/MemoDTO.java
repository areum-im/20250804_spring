package com.example.ex3.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // builder, allargs .., noargs .. 3개는 세트
public class MemoDTO {
  private Long mno;
  private String memoText;
}
