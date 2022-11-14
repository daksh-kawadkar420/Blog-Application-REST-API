package com.daksh.springboot.blogapplication.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDTO {
	private long id;
//	COMMENT NAME SHOULD NOT BE NULL OR EMPTY
	@NotEmpty(message = "TITLE SHOULD NOT BE NULL OR EMPTY")
	private String name;
//	EMAIL SHOULD NOT BE NULL OR EMPTY
//	EMAIL FIELD VALIDATION
	@NotEmpty(message = "EMAIL SHOULD NOT BE NULL OR EMPTY")
	@Email
	private String email;
//	BODY SHOULD NOT BE NULL OR EMPTY
//	BODY SHOULD CONTAIN ATLEAST 10 CHARACTERS
	@NotEmpty
	@Size(min = 10, message = "BODY SHOULD CONTAIN ATLEAST 10 CHARACTERS")
	private String body;
}
