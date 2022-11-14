package com.daksh.springboot.blogapplication.payload;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDTO {
	private Long id;
//	TITLE SHOULD NOT BE NULL OR EMPTY
//	TITLE SHOULD CONTAIN ATLEAST 2 CHARACTERS
	@NotEmpty
	@Size(min = 2, message = "Post title should contain atleast 2 characters.")
	private String title;
//	DESCRIPTION SHOULD NOT BE NULL OR EMPTY
//	DESCRIPTION SHOULD CONTAIN ATLEAST 10 CHARACTERS
	@NotEmpty
	@Size(min = 2, message = "Post description should contain atleast 10 characters.")
	private String description;
//	DESCRIPTION SHOULD NOT BE NULL OR EMPTY
	@NotEmpty
	private String content;
	private Set<CommentDTO> comments;
}
