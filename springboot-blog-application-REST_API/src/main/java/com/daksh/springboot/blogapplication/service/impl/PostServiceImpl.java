package com.daksh.springboot.blogapplication.service.impl;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.daksh.springboot.blogapplication.entity.Post;
import com.daksh.springboot.blogapplication.exception.ResourceNotFoundException;
import com.daksh.springboot.blogapplication.payload.PostDTO;
import com.daksh.springboot.blogapplication.payload.PostResponse;
import com.daksh.springboot.blogapplication.repository.PostRepository;
import com.daksh.springboot.blogapplication.service.PostService;


@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	
	private ModelMapper mapper;
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

//	METHOD TO CONVERT ALL ENTITY TO DTO
	private PostDTO mapToDTO(Post newPost) {
		PostDTO postResponse = mapper.map(newPost, PostDTO.class); ;
		return postResponse;
	}
	
//	METHOD TO CONVERT ALL DTO TO ENTITY
	private Post mapToEntity(PostDTO postDTO) {
		Post post = mapper.map(postDTO, Post.class); ;
		return post;
	}
	
	
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		//CONVERT DTO TO ENTITY
		Post post = mapToEntity(postDTO);
		//SAVING ENTITY(POST) TO DATABASE
		Post newPost = postRepository.save(post);
		
		//CONVERT ENTITY TO DTO
		PostDTO postResponse = mapToDTO(newPost);
		return postResponse;
	}
	
	@Override
	public PostDTO getPostByID(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return mapToDTO(post);
	}

	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		post.setContent(postDTO.getContent());
		post.setDescription(postDTO.getDescription());
		post.setTitle(postDTO.getTitle());
		Post update = postRepository.save(post);
		return mapToDTO(update);
	}

	@Override
	public void deletePost(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.deleteById(id);
	}

	@Override
	public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        // get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDTO> content= listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setTotalPage(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;

	}
	
}
