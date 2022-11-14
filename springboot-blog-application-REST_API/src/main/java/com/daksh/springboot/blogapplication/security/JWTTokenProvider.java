package com.daksh.springboot.blogapplication.security;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.daksh.springboot.blogapplication.exception.BlogAPIException;

@Component
public class JWTTokenProvider {
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.jwt-expiration-milliseconds}")
	private int expiration;
	
	
	//GENERATE TOKEN
	public String generateToken(Authentication authentication) {
		
		String userName = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + expiration);
		
		String token = Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
		return token;
	}
	
	
	//RETRIEVE USER_NAME FROM TOKEN
	public String getUserNameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	//VALIDATE JWT TOKEN
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Signature");
		} catch (MalformedJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT Token");
		} catch (ExpiredJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT Token");
		} catch (UnsupportedJwtException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT Signature");
		} catch (IllegalArgumentException e) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims String is Empty");
		}
		
	}

	
	
}
