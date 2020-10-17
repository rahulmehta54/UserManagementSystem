package com.ums.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ums.entities.User;
import com.ums.exceptions.UserExistsException;
import com.ums.exceptions.UserNotFoundException;
import com.ums.repositories.UserRepository;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	
	//CreateUser Method
		public User createUser(User user) throws UserExistsException {
			User existinguser= userRepository.findByUsername(user.getUsername());
			
			if(existinguser!=null) {
				throw new UserExistsException("User Already exists in repository");
			}
			return userRepository.save(user);
		}
		
		//getUserById
		public Optional<User> getUserById(Long id) throws UserNotFoundException{
			Optional<User> user = userRepository.findById(id);
			if(!user.isPresent()) {
				throw new UserNotFoundException("User Not Found in user Repository");
			}
			return user;
		}


		//updateUserById
		public User updateUserById(Long id, User user) throws UserNotFoundException{
			Optional<User> optionnalUser = userRepository.findById(id);
			if(!optionnalUser.isPresent()) {
				throw new UserNotFoundException("User Not Found in user Repository,Provide the correct user id");
			}
			user.setId(id);
			return userRepository.save(user);
			
		}
		
		
		//deleteUserById
		public void deleteUserById(Long id) {
			
			Optional<User> optionnalUser = userRepository.findById(id);
			if(!optionnalUser.isPresent()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User Not Found in user Repository,Provide the correct user id");
			}
			userRepository.deleteById(id);
			
		}
		
		
		//getUserByUsername
		
		public User getUserByUsername(String username) {
			return userRepository.findByUsername(username);
		}
		
		
}
