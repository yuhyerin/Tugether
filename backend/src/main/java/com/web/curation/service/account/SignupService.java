package com.web.curation.service.account;

import org.springframework.http.ResponseEntity;

import com.web.curation.dto.account.User;


public interface SignupService {
	public ResponseEntity<Object> checkEmail(String email);
	public ResponseEntity<Object> findUserByEmail(String email);
	public ResponseEntity<Object> save(User user);

}
