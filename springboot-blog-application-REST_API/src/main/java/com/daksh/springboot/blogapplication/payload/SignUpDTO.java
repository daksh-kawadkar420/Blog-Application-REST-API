package com.daksh.springboot.blogapplication.payload;

import lombok.Data;

@Data
public class SignUpDTO {
	private String name;
	private String userName;
	private String email;
	private String password;
}
