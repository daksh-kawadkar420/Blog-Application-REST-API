package com.daksh.springboot.blogapplication.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.util.StringUtils;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

	//GET REQUIRED DEPENDENCIES
	@Autowired
	private JWTTokenProvider provider;
	
	@Autowired
	private CostumeUserDetailService costumeUserDetailService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// GET JWT TOKEN FROM HTTP REQUEST
		String token = getJwtFromRequest(request);
		//VALIDATE TOKEN
		if(StringUtils.hasText(token) && provider.validateToken(token)) {
			//GET USERNAME FROM TOKEN
			String userName = provider.getUserNameFromToken(token);
			//LOAD USER ASSOCIATED WITH TOKEN
			UserDetails userdetails = costumeUserDetailService.loadUserByUsername(userName);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userdetails, null, userdetails.getAuthorities());
			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			//SET SPRING SECURITY
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		filterChain.doFilter(request, response);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		} 
		return null;
	}

}
