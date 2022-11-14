package com.daksh.springboot.blogapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daksh.springboot.blogapplication.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	//FIND BY POSTId
	List<Comment> findByPostId(long postId);
}
