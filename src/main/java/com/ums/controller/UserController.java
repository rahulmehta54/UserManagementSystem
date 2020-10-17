package com.ums.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.ums.entities.User;
import com.ums.exceptions.UserExistsException;
import com.ums.exceptions.UserNotFoundException;
import com.ums.services.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	//Create User Method
	//@RequestBody Annotation
	//@PostMapping Annotation
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody User user,UriComponentsBuilder bilBuilder) {
		try {
			userService.createUser(user);
			HttpHeaders header=new HttpHeaders();
			header.setLocation(bilBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
			return new ResponseEntity<Void>(header,HttpStatus.CREATED);
		} catch (UserExistsException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	
	//getUserById
	@GetMapping("/users/{id}")
	public Optional<User> getUserById(@PathVariable("id") Long id) {
		try {
		 return userService.getUserById(id);
		}catch(UserNotFoundException ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,ex.getMessage());
		}
	}
	
	//updateUserById
	@PutMapping("/users/{id}")
	public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
		try {
			return userService.updateUserById(id, user);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	
	//deleteUserById
	@DeleteMapping("/users/{id}")
	public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
	}
}
