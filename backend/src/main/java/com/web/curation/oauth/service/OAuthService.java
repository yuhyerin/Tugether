package com.web.curation.oauth.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.web.curation.common.client.GoogleClient;
import com.web.curation.oauth.dto.GoogleOAuthCallbackDto;
import com.web.curation.oauth.dto.GoogleTokenResponseDto;
import com.web.curation.user.dto.GoogleLoginResponseDto;
import com.web.curation.user.dto.GoogleOauth2Dto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {
	
	private final GoogleClient googleClient;
	
	public GoogleLoginResponseDto loginByGoogle(GoogleOAuthCallbackDto callbackDto) {
		GoogleTokenResponseDto responseDto = googleClient.getAccessTokenForLogin(callbackDto.getCode());
		return null;
	
	}
	
//	public void createPostRequest(String code) {
//		String url = "https://oauth2.googleapis.com/token";
//		String client_id = "963926899908-ncql9skkc6bkmifvg9bhc9jv2asecrcd.apps.googleusercontent.com";
//		String client_secret = "";
//		String redirect_uri = "";
//		String grant_type = "authorization_code";
//
//		GoogleOauth2Dto params = GoogleOauth2Dto.builder()
//									.code(code)
//									.client_id(client_id)
//									.client_secret(client_secret)
//									.redirect_uri(redirect_uri)
//									.grant_type(grant_type)
//									.build();
//
//		// header
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/x-www-form-urlencoded");
//		// body
//		HttpEntity<GoogleOauth2Dto> httpEntity = new HttpEntity<>(params, headers);
//
//
//		RestTemplate restTemplate = new RestTemplate();
//		return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
//	}
	
	public void loginByGoogle(GoogleOauth2Dto oauthDto) {
		// TODO
		googleClient.getAccessTokenForLogin(oauthDto.getCode());
	}
	
	
//	public JwtResponse loginByFacebook(FacebookOAuthCallbackDto callbackDto) {
//        FacebookTokenResponseDto responseDto = facebookClient.getAccessTokenForLogin(callbackDto.getCode());
//        FacebookTokenDebugResponseDto debugResponseDto = facebookClient.debugToken(responseDto.getAccessToken());
//        String userId = debugResponseDto.getData().getUserId();
//
//        // DB에 기존 연동정보가 존재하는지 확인
//        Optional<OAuthToken> result = oAuthTokenRepository
//                .findOAuthTokenByServiceCodeAndServiceId(ServiceCode.FACEBOOK.getCode(), userId);
//
//        OAuthToken token;
//        if(result.isEmpty()){ // 처음 로그인한 경우
//            // 임시 회원가입 진행
//            token = registerBySns(
//                    userId,
//                    ServiceCode.FACEBOOK.getCode(),
//                    responseDto.getAccessToken()
//            );
//        } else {
//            token = updateAccessToken(result.get(), responseDto.getAccessToken());
//        }
//        return generateServiceTokenByMemberId(token.getMemberId());
//    }

}
