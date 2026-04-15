package com.contentflow.contentflow.repository;

import com.contentflow.contentflow.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}