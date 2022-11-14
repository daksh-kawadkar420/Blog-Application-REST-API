package com.daksh.springboot.blogapplication.payload;

import lombok.Data;

@Data
public class LoginDTO {
	private String userNameOrEmail;
	private String password;
}
