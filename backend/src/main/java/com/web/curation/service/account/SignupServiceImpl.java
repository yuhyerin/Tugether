package com.web.curation.service.account;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.web.curation.common.ResponseCode;
import com.web.curation.controller.dto.EmailCheckDto;
import com.web.curation.dto.BasicResponse;
import com.web.curation.entity.Profile;
import com.web.curation.entity.User;
import com.web.curation.exception.user.UserAlreadyExist;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

	private final FindService findService;
	
	private final UserRepo userRepo;

	private final ProfileRepo profileRepo;

	/**
	 * 이메일 중복체크 및 인증번호 메일 발송
	 * 
	 * @param email 이메일
	 * @return EmailCheckDto 이메일과 이메일로 전송된 인증번호
	 */
	public EmailCheckDto checkEmail(String email) {
		// 이메일 중복 체크
		boolean emailDuplicate = userRepo.existsByEmail(email);
		if(emailDuplicate) {
			throw new UserAlreadyExist();
		}
		// 인증번호 전송
		String certNumber = findService.sendCertificationNumber(email);
		
		return EmailCheckDto.builder()
				.email(email)
				.certNumber(certNumber)
				.build();
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
		} catch (IllegalArgumentException e) {
			response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return response;
	}

}
