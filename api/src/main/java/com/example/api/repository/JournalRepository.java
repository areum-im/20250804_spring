package com.example.api.repository;

import com.example.api.entity.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

// 화면 구상하면서 작성해야 함
public interface JournalRepository extends JpaRepository<Journal, Long>, SearchJournalRepository {

  @Query("select j, sum(coalesce(c, likes, 0)), count(distinct c)" +
      "from Journal j left outer join Comments c" +
      "on c.journal = j where j.members.mid = :mid group by j")
  Page getListMyJournal(Pageable pageable, @Param("mid") Long mid);

  @Query("select j, p, sum(coalesce(c, likes, 0)), count(distinct c)" +
      "from Journal j " +
      "left outer join Photos p on p.journal = j " +
      "left outer join Comments c on c.journal = j " +
      "where j.members.mid = :mid group by j ")
  Page getListMyJournalPhotos(Pageable pageable, @Param("mid") Long mid);

  @Query("select j, p, sum(coalesce(c, likes, 0)), count(distinct c)" +
      "from Journal j " +
      "left outer join Photos p on p.journal = j " +
      "left outer join Comments c on c.journal = j " +
      "where p.pno = (select max(p2.pno) from Photos p2 where p2.journal = j )" +
      "and j.members.mid = :mid group by j ")
  Page getListMyJournalPhotosDefault(Pageable pageable, @Param("mid") Long mid);

  @Query("select j, p, sum(coalesce(c, likes, 0)), count(distinct c)" +
      "from Journal j " +
      "left outer join Photos p on p.journal = j " +
      "left outer join Comments c on c.journal = j " +
      "where p.pno = (select max(p2.pno) from Photos p2 where p2.journal = j )" +
      "group by j ")
  Page getListAllJournalPhotosDefault(Pageable pageable); // 모든 journal 목록 보기 :: main

  @Query("select j, sum(coalesce(c, likes, 0)), count(distinct c)" +
      "from Journal j " +
      "left outer join Photos p on p.journal = j " +
      "left outer join Comments c on c.journal = j " +
      "left outer join Members m on m.members = m " +
      "where j.jno = :jno " +
      "group by p ")
  List<Object[]> getJournalWithAll(@Param("jno") Long jno); // journal 상세보기



}
