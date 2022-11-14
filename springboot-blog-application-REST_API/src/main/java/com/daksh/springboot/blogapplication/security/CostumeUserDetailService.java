package com.daksh.springboot.blogapplication.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.daksh.springboot.blogapplication.repository.UserRepository;
import com.daksh.springboot.blogapplication.entity.Role;
import com.daksh.springboot.blogapplication.entity.User;

@Service
public class CostumeUserDetailService implements UserDetailsService {

	private UserRepository userRepository;
	
	public CostumeUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(userNameOrEmail, userNameOrEmail).orElseThrow(() -> 
		new UsernameNotFoundException("USER NOT FOUND OF GIVEN USERNAME OR EMAIL" + userNameOrEmail));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), 
				user.getPassword(),mapRolesToAuthorities(user.getRoles()));
	}
	//METHOD TO MAP ROLES TO GIVEN USER
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
