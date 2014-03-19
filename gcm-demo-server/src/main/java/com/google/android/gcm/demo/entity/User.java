package com.google.android.gcm.demo.entity;

import org.springframework.data.annotation.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/** * Created by arkadii.tetelman on 3/19/14. */
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "userId")
	private long userId;
	@Column
	private String userEmail;
	@Column
	private String userPassword;
	@Column
	private String googleAPIKey;

	public User(long userId, String userEmail, String userPassword,
			String googleAPIKey) {
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.googleAPIKey = googleAPIKey;
	}

	public User() {
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getGoogleAPIKey() {
		return googleAPIKey;
	}

	public void setGoogleAPIKey(String googleAPIKey) {
		this.googleAPIKey = googleAPIKey;
	}
}
