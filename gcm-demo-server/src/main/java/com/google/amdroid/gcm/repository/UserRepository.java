package com.google.amdroid.gcm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.google.android.gcm.demo.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

}