package com.web.curation.service.account;

import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.http.ResponseEntity;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.account.User;

public interface FindService {
	
	public String sendTest(String email) throws MessagingException;
	public ResponseEntity<Object> findUserByEmail(String email) throws MessagingException ;
	public void sendMail(String email) throws MessagingException;
	public User changePasswordByEmail(String email);
	public boolean checkPW(String email, String password);
	public ResponseEntity<Map<String,Object>> changePW(User u);
}
