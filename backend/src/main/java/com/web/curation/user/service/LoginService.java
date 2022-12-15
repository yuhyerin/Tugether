package com.web.curation.user.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.web.curation.entity.OAuthPlatform;
import com.web.curation.entity.User;
import com.web.curation.exception.TugetherException;
import com.web.curation.exception.user.NotExistEmailException;
import com.web.curation.exception.user.PasswordErrorException;
import com.web.curation.exception.user.OtherPlatformLoginUserException;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.oauth.service.OAuthService;
import com.web.curation.user.dto.GoogleLoginRequestDto;
import com.web.curation.user.dto.GoogleLoginResponseDto;
import com.web.curation.user.dto.GoogleOauth2Dto;
import com.web.curation.user.dto.LoginDto;
import com.web.curation.user.repo.UserRepo;
import com.web.curation.util.CipherUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

	private final SignupService signupService;
	private final OAuthService oAuthService;
	private final JwtService jwtService;
	private final UserRepo userRepo;

	@Value("${CLIENT_ID}")
	private String CLIENT_ID;

	/**
	 * 로그인
	 * 
	 * @param email    이메일
	 * @param password 비밀번호
	 * @return LoginDto 이메일, 닉네임, jwtToken
	 */
	public LoginDto login(User user) {

		User findUser = userRepo.findUserByEmail(user.getEmail()).orElseThrow(() -> new NotExistEmailException());
		if (findUser.getOauthPlatform() == OAuthPlatform.TUGETHER) {
			// 아이디, 비밀번호 검증
			if (CipherUtil.decrypt(findUser.getPassword()).equals(user.getPassword())) {
				String token = jwtService.createJwtToken(findUser); // 토큰 생성
				return LoginDto.builder().email(findUser.getEmail()).nickname(findUser.getNickname()).jwtToken(token)
						.build();

			} else {
				throw new PasswordErrorException();
			}

		} else {
			// 소셜 로그인 회원
			throw new OtherPlatformLoginUserException();
		}

	}

	/**
	 * 구글 연동 로그인
	 * 
	 * @param email     이메일
	 * @param password  비밀번호
	 * @param nickname  닉네임
	 * @param gender    성별
	 * @param birthYear 생년
	 * @return GoogleLoginDto 구글 연동 로그인 사용자 이메일, 이름
	 */
	public GoogleLoginResponseDto googleLogin(GoogleLoginRequestDto loginDto) {

		NetHttpTransport transport = new NetHttpTransport();
		JsonFactory jsonFactory = new GsonFactory();

		String googletoken = loginDto.getGoogletoken().replaceAll("\"", "");
		log.debug("google token >>>", googletoken);

		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList(CLIENT_ID)).build();

		try {
			GoogleIdToken idToken = verifier.verify(googletoken);
			
			if(idToken == null) {
				throw new TugetherException("해당 google토큰이 유효하지 않습니다. ");
			}
			Payload payload = idToken.getPayload();
			// Print user identifier
			String userId = payload.getSubject();
			// Get profile information from payload
			String email = payload.getEmail();
			boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			String nickname = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			String locale = (String) payload.get("locale");
			String familyName = (String) payload.get("family_name");
			String givenName = (String) payload.get("given_name");
			log.info("[google 연동 정보] >>>" + userId + "/" + email + "/" + emailVerified + "/" + nickname + "/"
					+ pictureUrl + "/" + locale + "/" + familyName+"/"+givenName);
			

			Optional<User> findUser = userRepo.findUserByEmail(email);
			User googleLoginUser;
			
			if(!findUser.isPresent()) {
				// 신규 구글 연동 -> 회원가입 진행
				
				
				googleLoginUser = User.builder()
										.email(email)
										.password("임시비밀번호")
										.nickname("임시닉네임")
										.birthYear(1999)
										.gender('M')
										.oauthPlatform(OAuthPlatform.GOOGLE)
										.build();
				signupService.save(googleLoginUser);
				
			}else if(findUser.get().getOauthPlatform().equals(OAuthPlatform.GOOGLE)) {
				googleLoginUser = findUser.get();
				
			}else {
				// 타 서비스 연동회원
				throw new OtherPlatformLoginUserException();
			}
			
			String token = jwtService.createJwtToken(googleLoginUser); // 토큰 생성
			return GoogleLoginResponseDto.builder()
					.email(googleLoginUser.getEmail())
					.nickname(googleLoginUser.getNickname())
					.jwtToken(token)
					.build();

		} catch (GeneralSecurityException e) {
			throw new TugetherException();
			
		} catch (IOException e) {
			throw new TugetherException();
			
		}
	}
	
	public GoogleLoginResponseDto googleLogin2(GoogleOauth2Dto loginDto) {
		// request
//		oAuthService.createPostRequest(loginDto.getCode());
		
		// access token
//		oAuthService.getAccessToken(loginDto);
		
		return null;
	}

}
