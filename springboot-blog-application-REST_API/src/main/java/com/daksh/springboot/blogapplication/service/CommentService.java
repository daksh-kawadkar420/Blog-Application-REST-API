package com.daksh.springboot.blogapplication.service;

import java.util.List;

import com.daksh.springboot.blogapplication.payload.CommentDTO;

public interface CommentService {
	//CREATE COMMENT METHOD
	CommentDTO createComment(long postId, CommentDTO commentdto);
	// GET ALL COMMENTS BY POSTId
	List<CommentDTO> getAllCommentsByPostId(long postId);
	// GET COMMENT BY ID
	CommentDTO getCommentByID(long postId, long commentId);
	// UPDATE COMMENT BY ID IF IT BELONGS TO GIVEN POSTID
	CommentDTO updateComment(long postId, long commentId, CommentDTO commentReq);
	// DELETE COMMENT BY ID IF IT BELONGS TO GIVEN POSTID
	void deleteCommentById(long postId, long commentId);
}
