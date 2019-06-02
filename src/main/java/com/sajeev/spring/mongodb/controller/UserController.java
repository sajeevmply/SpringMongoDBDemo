package com.sajeev.spring.mongodb.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sajeev.spring.mongodb.dao.UserDAO;
import com.sajeev.spring.mongodb.dao.UserRepository;
import com.sajeev.spring.mongodb.logger.MyLogger;
import com.sajeev.spring.mongodb.model.User;

@RestController
@RequestMapping(value = "/")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDAO userDAO;
	
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@MyLogger(exceptionLogLevel=LogLevel.ERROR,isCalculateTime=true,logLevel=LogLevel.INFO,isLogMethodParams=false,isLogReturnValue=true)
	public List<User> getAllUsers() {
		LOG.info("Getting all users.");
		return userDAO.getAllUsers();
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	@MyLogger(exceptionLogLevel=LogLevel.ERROR,isCalculateTime=true,logLevel=LogLevel.INFO,isLogMethodParams=true,isLogReturnValue=true)
	public User getUser(@PathVariable String userId) {
		LOG.info("Getting user with ID: {}.", userId);
		Optional<User> findById = userRepository.findById(userId);
		return findById.get();
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@MyLogger(exceptionLogLevel=LogLevel.ERROR,isCalculateTime=true,logLevel=LogLevel.INFO,isLogMethodParams=true,isLogReturnValue=true)
	public User addNewUsers(@RequestBody User user) {
		LOG.info("Saving user.");
		return userRepository.save(user);
	}
	
	@RequestMapping(value = "/settings/{userId}/{key}", method = RequestMethod.GET)
	@MyLogger(exceptionLogLevel=LogLevel.ERROR,isCalculateTime=true,logLevel=LogLevel.INFO,isLogMethodParams=true,isLogReturnValue=true)
	public String getUserSetting(@PathVariable String userId, @PathVariable String key) {
		Optional<User> user = userRepository.findById(userId);
		if (user != null) {
			return user.get().getUserSettings().get(key);
		} else {
			return "User not found.";
		}
	}
	
	@RequestMapping(value = "/settings/{userId}", method = RequestMethod.GET)
	@MyLogger(exceptionLogLevel=LogLevel.ERROR,isCalculateTime=true,logLevel=LogLevel.INFO,isLogMethodParams=true,isLogReturnValue=true)
	public Object getAllUserSettings(@PathVariable String userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user != null) {
			return user.get().getUserSettings();
		} else {
			return "User not found.";
		}
	}
	
	@RequestMapping(value = "/settings/{userId}/{key}/{value}", method = RequestMethod.GET)
	@MyLogger(exceptionLogLevel=LogLevel.ERROR,isCalculateTime=true,logLevel=LogLevel.INFO,isLogMethodParams=true,isLogReturnValue=true)
	public String addUserSetting(@PathVariable String userId, @PathVariable String key, @PathVariable String value) {
		Optional<User> user = userRepository.findById(userId);
		if (user != null) {
			user.get().getUserSettings().put(key, value);
			userRepository.save(user.get());
			return "Key added";
		} else {
			return "User not found.";
		}
	}
}