package com.web.curation.user.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.web.curation.entity.Profile;
import com.web.curation.entity.User;
import com.web.curation.exception.user.UserAlreadyExist;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.user.controller.dto.EmailCheckDto;
import com.web.curation.user.controller.dto.SignUpDto;
import com.web.curation.user.repo.UserRepo;

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
		// 인증번호 메일 발송
		String certNumber = findService.sendCertificationNumber(email);
		
		return EmailCheckDto.builder()
				.email(email)
				.certNumber(certNumber)
				.build();
	}

	/**
	 * 회원가입
	 * 
	 * @param email 이메일
	 * @param password 비밀번호 
	 * @param nickname 닉네임
	 * @param gender 성별
	 * @param birthYear 생년
	 * @return SignUpDto 회원가입 완료된 사용자 이메일, 이름
	 */
	@Override
	public SignUpDto save(User user) {
		
		// 유저
		User signupUser = userRepo.save(user);
		
		// 프로필
		Profile profile = Profile.builder()
				.email(signupUser.getEmail())
				.profilePhoto("default.png")
				.nickname(signupUser.getNickname())
				.build();
		profileRepo.save(profile);
		
		return SignUpDto.builder()
				.email(signupUser.getEmail())
				.nickname(signupUser.getNickname())
				.build();
	}

}
