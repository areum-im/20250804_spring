package com.example.api.repository;

import com.example.api.entity.Comments;
import com.example.api.entity.Journal;
import com.example.api.entity.Members;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.Text;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
  @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
  List<Comments> findByJournal(Journal journal);

  @Modifying
  @Query("delete from Comments c where c.members = :members ")
  void deleteByMembers(Members members);

  @Modifying
  // comments에 jno가 없고 journal이 있기 때문에 c.journal.jno로 가야 함
  @Query("delete from Comments c where c.journal.jno = :jno ")
  void deleteByJno(Long jno);


}
