package com.daksh.springboot.blogapplication.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.daksh.springboot.blogapplication.security.CostumeUserDetailService;
import com.daksh.springboot.blogapplication.security.JWTAuthenticationEntryPoint;
import com.daksh.springboot.blogapplication.security.JWTAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CostumeUserDetailService userDetailService;
	
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			.antMatchers("/api/auth/**").permitAll()
			.antMatchers("/v2/api-docs/**").permitAll() //THIS END-POINT IS FOR SWAGGER API
			.antMatchers("/swagger-ui/**").permitAll()
            .antMatchers("/swagger-resources/**").permitAll()
            .antMatchers("/swagger-ui.html").permitAll()
            .antMatchers("/webjars/**").permitAll()
			.anyRequest()
			.authenticated();
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
