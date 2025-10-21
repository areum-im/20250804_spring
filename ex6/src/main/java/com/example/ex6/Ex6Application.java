package com.example.ex6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
// BasicEntity에 있는 @CreateDate, @LastModifiedDate 필드가
// DB에 저장되거나 변경될 때 자동으로 시간이 기록됨
@SpringBootApplication
public class Ex6Application {

  public static void main(String[] args) {
    SpringApplication.run(Ex6Application.class, args);
		System.out.println("http://localhost:8080/ex6");
  }

}
