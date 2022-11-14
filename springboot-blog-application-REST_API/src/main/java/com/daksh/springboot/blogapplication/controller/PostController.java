package com.daksh.springboot.blogapplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daksh.springboot.blogapplication.payload.PostDTO;
import com.daksh.springboot.blogapplication.payload.PostResponse;
import com.daksh.springboot.blogapplication.service.PostService;
import com.daksh.springboot.blogapplication.utils.AppConstraints;


@RequestMapping("/api/post")
@RestController
public class PostController {
	//HERE IS THE OBJECT OF THE INTERFACE, NOT OF THE CLASS, BCZ OF LOOSE COUPLING WITH THE CLASS, 
	//(ALWAYS CHOOSE INTERFACE NOT CLASS AS A BEST PRACTICE)
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	//CREATE BLOG POST
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
		return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
	}
	
	//GET ALL POSTS
	@GetMapping
	public PostResponse getAllPost(
		@RequestParam(value = "pageNo", defaultValue = AppConstraints.pageNo, required = false) int pageNo, 
		@RequestParam(value = "pageSize", defaultValue = AppConstraints.pageSize, required = false) int pageSize, 
		@RequestParam(value = "sortBy", defaultValue = AppConstraints.sortBy, required = false) String sortBy, 
		@RequestParam(value = "sortDir", defaultValue = AppConstraints.sortDir, required = false) String sortDir
	) {
		return postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
	}
	
	//GET POST BY ID
	@GetMapping("/{id}")
	public ResponseEntity<PostDTO> getPostById( @PathVariable(name = "id") long id ) {
//		return ResponseEntity.ok(postService.getPostByID(id));
		return new ResponseEntity<>(postService.getPostByID(id), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	//UPDATE POST
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") long id) {
		PostDTO postResponse = postService.updatePost(postDTO, id);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	//DELETE POST BY ID
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
		postService.deletePost(id);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}
}
