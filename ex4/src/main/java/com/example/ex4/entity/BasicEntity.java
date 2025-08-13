package com.example.ex4.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 공통된 mapping 정보를 상속받기 위한 클래스에 사용
@MappedSuperclass
// entity의 객체가 생성, 변경되는 것을 자동으로 감지 후 반영
@EntityListeners(value = {AuditingEntityListener.class})
abstract class BasicEntity { // entity 안에서만 사용할 거기 때문에 abstract 사용
  @CreatedDate
  @Column(name = "regdate", updatable = false) // 처음 들어간 값이 수용 안되므로 false로 설정
  private LocalDateTime regDate;

  @LastModifiedDate
  @Column(name = "moddate")

  private LocalDateTime modDate;
}
