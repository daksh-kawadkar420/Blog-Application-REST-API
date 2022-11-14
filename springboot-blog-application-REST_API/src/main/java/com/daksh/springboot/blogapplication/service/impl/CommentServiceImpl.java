package com.daksh.springboot.blogapplication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.daksh.springboot.blogapplication.entity.Comment;
import com.daksh.springboot.blogapplication.entity.Post;
import com.daksh.springboot.blogapplication.exception.BlogAPIException;
import com.daksh.springboot.blogapplication.exception.ResourceNotFoundException;
import com.daksh.springboot.blogapplication.payload.CommentDTO;
import com.daksh.springboot.blogapplication.repository.CommentRepository;
import com.daksh.springboot.blogapplication.repository.PostRepository;
import com.daksh.springboot.blogapplication.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	
//	METHOD TO CONVERT ALL ENTITY TO DTO
	private CommentDTO mapToDTO(Comment newComment) {
		CommentDTO commentResponse = mapper.map(newComment, CommentDTO.class); ;
		return commentResponse;
	}
	
	
//	METHOD TO CONVERT ALL DTO TO ENTITY
	private Comment mapToEntity(CommentDTO commentDTO) {
		Comment comment = mapper.map(commentDTO, Comment.class); ;
		return comment;
	}
	
	
	//CREATE COMMENT TO POST
	@Override
	public CommentDTO createComment(long postId, CommentDTO commentdto) {
		// CONVERTING DTO OBJECT TO ENTITY
		Comment comment = mapToEntity(commentdto);
		//FIND POST BY ID
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		//SET POST TO COMMENT ENTITY
		comment.setPost(post);
		// SET COMMENT ENTITY TO DATABASE
		commentRepository.save(comment);
		return mapToDTO(comment);
	}

	
	// RETRIEVE ALL COMMENTS BELONGS TO GIVEN POSTID
	@Override
	public List<CommentDTO> getAllCommentsByPostId(long postId) {
		// RETRIEVE COMMENTS BY POSTID
		List<Comment> comments = commentRepository.findByPostId(postId);
		// RETURNING AFTER CONVERTING COMMENT ENTITIES TO COMMENTDTOS
		return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
	}


	
	
	// GET COMMENT BY ID BELONGS TO A POSTID
	@Override
	public CommentDTO getCommentByID(long postId, long commentId) {
		// RETRIEVE POST BY POSTID
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
		//RETRIEVE COMMENT BY COMMENTID
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
		// CHECK IF THE RETRIEVED COMMENT BELONGS TO THE GIVEN POST(POSTID)
		if(!comment.getPost().getId().equals(postId)) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Given Comment does not belong to given post");
		}
		return mapToDTO(comment);
	}

	// UPDATE COMMENT BY ID IF IT BELONGS TO GIVEN POSTID
	@Override
	public CommentDTO updateComment(long postId, long commentId, CommentDTO commentReq) {
		// RETRIEVE POST BY POSTID
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		//RETRIEVE COMMENT BY COMMENTID
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if(!comment.getPost().getId().equals(postId)) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Given Comment does not belong to given post");
		}
		comment.setName(commentReq.getName());
		comment.setEmail(commentReq.getEmail());
		comment.setBody(commentReq.getBody());
		Comment commentResponse = commentRepository.save(comment);
		return mapToDTO(commentResponse);
	}


	@Override
	public void deleteCommentById(long postId, long commentId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		//RETRIEVE COMMENT BY COMMENTID
		Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if(!comment.getPost().getId().equals(postId)) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Given Comment does not belong to given post");
		}
		commentRepository.delete(comment);
	}

	
	
}
