package com.web.curation.user.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.web.curation.entity.User;
import com.web.curation.user.dto.FindPasswordDto;

public interface FindService {
	
	public String sendCertificationNumberMail(String email);
	public String sendTempPasswordMail(String email);
	public FindPasswordDto findPassword(String email);
	public User changePasswordByEmail(String email);
	public boolean checkPW(String email, String password);
	public ResponseEntity<Map<String,Object>> changePW(User u);
}
