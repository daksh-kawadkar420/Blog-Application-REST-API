package com.daksh.springboot.blogapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daksh.springboot.blogapplication.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
