package com.daksh.springboot.blogapplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daksh.springboot.blogapplication.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String userName);
}
