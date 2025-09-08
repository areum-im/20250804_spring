package com.example.ex8.repository;

import com.example.ex8.entity.Note;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

  // 단건 조회 : writer까지 즉시 로딩
  // type = LOAD는 지정 속성만 즉시 로딩으로 바꿔서 함께 가져오게 하는 방식
  @EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD)
  @Query("select n from Note n where n.num= :num ")
  Optional<Note> getWithWriter(Long num);



// 리스트 조회 : 특정 이메일의 작성자 글 목록 + writer 즉시 로딩
// 만약에 ClubMember의 roleSet까지 가져 올 때는 {"writer", "writer.roleSet"} 하면 됨
  @EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD) //eager 방법으로 join ??
  @Query("select n from Note n where n.writer.email= :email ")
  List<Note> getList(String email);


}
