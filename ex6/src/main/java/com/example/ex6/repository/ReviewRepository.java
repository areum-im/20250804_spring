package com.example.ex6.repository;

import com.example.ex6.entity.Member;
import com.example.ex6.entity.Movie;
import com.example.ex6.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

  // Review에서 Member가 LAZY로 되었지만 이메서드에서는 조인을 하겠다는 표시
  // @EntityGraph : 목록 그릴 때 닉네임/프로필 등 연관 데이터가 바로 필요하면 @EntityGraph로 같이 가져오면 성능이 좋아짐
  @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
  List<Review> findByMovie(Movie movie);

  // 쿼리메서드는 Review 개수만큼 delete 발생 쿼리@은 JPQL 조건절이용해서 한번에 처리
  @Modifying
  @Query("delete from Review r where r.member = :member ")
  void deleteByMember(Member member);

  @Modifying
  @Query("delete from Review r where r.movie.mno = :mno ")
  void deleteByMno(@Param("mno") Long mno);
}