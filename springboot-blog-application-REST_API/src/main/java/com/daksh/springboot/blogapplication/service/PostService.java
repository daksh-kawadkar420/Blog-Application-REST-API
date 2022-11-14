package com.daksh.springboot.blogapplication.service;

import java.util.List;

import com.daksh.springboot.blogapplication.payload.PostDTO;
import com.daksh.springboot.blogapplication.payload.PostResponse;

public interface PostService {
//	CREATE POST
	PostDTO createPost(PostDTO postDTO);
//	GET ALL POST
	PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
//	GET POST BY ID
	PostDTO getPostByID(long id);
//	UPDATE POST BY ID
	PostDTO updatePost(PostDTO postDTO, long id);
//	DELETE POST BY ID
	void deletePost(long id);
}
