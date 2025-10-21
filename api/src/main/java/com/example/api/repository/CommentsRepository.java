package com.example.api.repository;

import com.example.api.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.Text;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
