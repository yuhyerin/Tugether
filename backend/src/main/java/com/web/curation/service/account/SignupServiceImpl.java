package com.web.curation.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.account.User;
import com.web.curation.dto.profile.Profile;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.repo.UserRepo;

@Service
public class SignupServiceImpl implements SignupService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ProfileRepo profileRepo;
	

	@Override
	public ResponseEntity<Object> checkEmail(String email) {
		System.out.println("checkEmail: "+email);
		BasicResponse result = new BasicResponse();
		result.status = true;
		try {
			String test = userRepo.findEmailByEmail(email);
			System.out.println(test);
			result.data = "error";
			if ("".equals(test) || test==null)
				result.data = "success";
		} catch (NullPointerException e) {
			result.data = "success";
		}
		ResponseEntity<Object> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}


	@Override
	public ResponseEntity<Object> findUserByEmail(String email) {
		ResponseEntity<Object> response = null;
		BasicResponse result = new BasicResponse();
		result.status = true;
		try {
			User u = userRepo.findUserByEmail(email);
			result.data = "error";
			if (u.getEmail() != null)
				result.data = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (NullPointerException e) {
			result.data = "error";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;

	}

	@Override
	public ResponseEntity<Object> save(User user) {
		ResponseEntity<Object> response = null;
		BasicResponse result = new BasicResponse();

		try {
    		userRepo.save(user);
    		Profile p = Profile.builder().email(user.getEmail()).profile_photo("default.png")
    				.nickname(user.getNickname()).build();
    		profileRepo.save(p);
    		result.status = true;
        	result.data = "success";
        	response = new ResponseEntity<>(result, HttpStatus.OK);
    	}catch(IllegalArgumentException e) {
    		response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
		return response;
	}

}
