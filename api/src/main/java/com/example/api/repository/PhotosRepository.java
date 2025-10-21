package com.example.api.repository;

import com.example.api.entity.Journal;
import com.example.api.entity.Photos;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotosRepository extends JpaRepository<Photos, Long> {
  @Modifying
  @Query("delete from Photos p where p.journal.jno = :jno")
  void deleteByJno(Long jno);

  @Modifying
  @Query("delete from Photos p where p.uuid = :uuid")
  void deleteByUuid(String uuid);

  @EntityGraph(attributePaths = {"journal"}, type = EntityGraph.EntityGraphType.FETCH)
  List<Photos> findByJno(Journal journal);


}