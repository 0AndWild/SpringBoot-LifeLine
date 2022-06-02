package com.mymy.blog.repository;

import com.mymy.blog.domain.Blog;
import com.mymy.blog.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByOrderByCreatedAtDesc();
}
