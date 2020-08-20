package com.web.curation.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.web.curation.dto.account.AuthenticationResponse;
import com.web.curation.dto.account.User;
import com.web.curation.repo.UserRepo;

@Service
public class LoginService {
	
	@Autowired
    private UserRepo userRepo;
	
	// 로그인 계정은 고정값을 이용한다.

	public AuthenticationResponse login(String email, String password) {
		System.out.println("로그인서비스!!!"+email+"    "+password);
		if( userRepo.findUserByEmailAndPassword(email, password)!=null ) {
			User loginuser =  userRepo.findUserByEmailAndPassword(email, password);
			AuthenticationResponse authuser = new AuthenticationResponse(loginuser.getEmail(),
					loginuser.getNickname(),
					loginuser.getTemp());
			return authuser;
		}else {
			throw new RuntimeException("그런사람은 존재하지 않습니다!");
		}

	}

	public User isPresentEmail(String email) {
		
		User user=null;
		try {
			user = userRepo.findUserByEmail(email);
		}catch (NullPointerException e) {
			System.out.println("해당회원은 존재하지 않습니다.");
		}
		return user;
	}

	public boolean isGoogle(String email) {
		if(userRepo.findGoogleUserByPassword(email) == null) { 
			return false;
		}
		// 해당회원이 비번이 null이라면 -> 소셜로그인회원 
		return true;
	}
	
}
