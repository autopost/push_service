package com.google.amdroid.gcm.repository;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.android.gcm.demo.entity.User;

public class UserRepositoryImpl {

	@Autowired
	private UserRepository repo;

	public User getUser() {
	
		return repo.findOne("");
	}

}
