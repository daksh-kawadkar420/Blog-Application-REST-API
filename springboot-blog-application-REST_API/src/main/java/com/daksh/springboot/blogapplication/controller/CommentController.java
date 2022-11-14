package com.daksh.springboot.blogapplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daksh.springboot.blogapplication.payload.CommentDTO;
import com.daksh.springboot.blogapplication.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	//CREATE COMMENT
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable(name = "postId") long postId,@Valid @RequestBody CommentDTO CommentDto) {
		return new ResponseEntity<CommentDTO>(commentService.createComment(postId, CommentDto), HttpStatus.CREATED);
	}
	
	//GET ALL COMMENTS BY POSTID
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDTO> getAllComments(@PathVariable(name = "postId") long postId) {
		return commentService.getAllCommentsByPostId(postId);
	}
	
	//GET COMMENT BY ID AND POSTID
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> getCommentbyId(@PathVariable(name = "postId") long postId,@PathVariable(name = "commentId") long commentId) {
		return new ResponseEntity<CommentDTO>(commentService.getCommentByID(postId, commentId), HttpStatus.OK);
	}
	
	
	// UPDATE COMMENT BY COMMENTID IF TI BELONGS TO GIVEN POST
	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable(name = "postId") long postId,
			@PathVariable(name = "commentId") long commentId,
			@Valid @RequestBody CommentDTO commentDto) {
		return new ResponseEntity<CommentDTO>(commentService.updateComment(postId, commentId, commentDto),HttpStatus.OK);
	}
	
	
	// DELETE COMMENT BY COMMENTID IF TI BELONGS TO GIVEN POST
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") long postId,@PathVariable(name = "commentId") long commentId) {
		commentService.deleteCommentById(postId, commentId);
		return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.OK);
	}
}
