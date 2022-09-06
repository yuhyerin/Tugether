package com.web.curation.service.account;

import org.springframework.http.ResponseEntity;

import com.web.curation.controller.dto.EmailCheckDto;
import com.web.curation.entity.User;


public interface SignupService {
	public EmailCheckDto checkEmail(String email);
//	public ResponseEntity<Object> checkEmail(String email);
	public ResponseEntity<Object> findUserByEmail(String email);
	public ResponseEntity<Object> save(User user);

}
