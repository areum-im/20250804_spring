package com.example.ex6.repository;

import com.example.ex6.entity.Movie;
import com.example.ex6.entity.MovieImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieImageRepository extends JpaRepository<MovieImage, Long> {
  @Modifying
  @Query("delete from MovieImage mi where mi.uuid=:uuid ")
  void deleteByUuid(@Param("uuid") String uuid);

  @Query("select mi from MovieImage mi where mi.movie.mno=:mno")
  List<MovieImage> findByMno(@Param("mno") Long mno);
  // modify()에서 기존 이미지 조회 후 삭제할 때 사용
  List<MovieImage> findByMovie(Movie movie);

  @Modifying
  @Query("delete from MovieImage mi where mi.movie.mno=:mno")
  void deleteByMno(@Param("mno") long mno);
  // 조회 없이 한 번에 이미지 싹 지울 때 사용
  void deleteByMovie(@Param("movie")Movie movie);
}