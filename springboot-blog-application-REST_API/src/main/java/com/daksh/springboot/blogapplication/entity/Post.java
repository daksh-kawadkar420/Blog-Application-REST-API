package com.daksh.springboot.blogapplication.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//EQUALAVALENT TO GETTERS, SETTERS, CONSTRUCTORS, TOSTRING, EQUALS&HASHCODE with this we don't need to add these methods
@AllArgsConstructor //equalavalent to all non paramaterize and paramaterize constructors with this we don't need to add these constructors
@NoArgsConstructor
//WE HAVE NOT USED @Data ANNOTATION BECAUSE IT ALSO INCLUDES TOSTRING() METHOD, 
//WHICH IS CAUSING INFINITE LOOP AND STACKTRACEERROR IN MODELMAPPER DURING CONVERTING POST ENTITY INTO POSTDTO CLASS.
@Entity
@Getter
@Setter
@Table (
	name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}	
)
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(name = "description", nullable = false)
	private String description;
	@Column(name = "content", nullable = false)
	private String content;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();
	
}
