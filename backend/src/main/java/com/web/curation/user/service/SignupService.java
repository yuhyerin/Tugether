package com.web.curation.user.service;

import com.web.curation.entity.User;
import com.web.curation.user.dto.EmailCheckDto;
import com.web.curation.user.dto.SignUpDto;


public interface SignupService {
	public EmailCheckDto checkEmail(String email);
	public SignUpDto save(User user);

}
