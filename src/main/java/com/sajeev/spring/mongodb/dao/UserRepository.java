package com.sajeev.spring.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sajeev.spring.mongodb.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
