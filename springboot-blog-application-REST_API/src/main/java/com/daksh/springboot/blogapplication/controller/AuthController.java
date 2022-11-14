package com.daksh.springboot.blogapplication.controller;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daksh.springboot.blogapplication.entity.Role;
import com.daksh.springboot.blogapplication.entity.User;
import com.daksh.springboot.blogapplication.payload.JWTAuthResponse;
import com.daksh.springboot.blogapplication.payload.LoginDTO;
import com.daksh.springboot.blogapplication.payload.SignUpDTO;
import com.daksh.springboot.blogapplication.repository.RoleRepository;
import com.daksh.springboot.blogapplication.repository.UserRepository;
import com.daksh.springboot.blogapplication.security.JWTTokenProvider;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JWTTokenProvider provider;
	
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDto) {
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), 
				loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		//GET TOKEN FROM TOKEN_PROVIDER
		
		String token = provider.generateToken(auth);
		
		return ResponseEntity.ok(new JWTAuthResponse(token));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signupDto) {
//		CHECK FOR GIVEN USERNAME IN DB
		if(userRepository.existsByUsername(signupDto.getUserName())) {
			return new ResponseEntity<String>("Given Username already Exist.", HttpStatus.BAD_REQUEST);
		}
//		CHECK FOR GIVEN EMAIL IN DB
		if(userRepository.existsByEmail(signupDto.getEmail())) {
			return new ResponseEntity<String>("Given Email already Exist.", HttpStatus.BAD_REQUEST);
		}
//		REGISTER USER
		User user = new User();
		user.setName(signupDto.getName());
		user.setEmail(signupDto.getEmail());
		user.setUsername(signupDto.getUserName());
		user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		userRepository.save(user);
		return new ResponseEntity<String>("SignedUp Successfully", HttpStatus.CREATED);
		
	}
	
}
