package com.web.curation.common.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.reactive.function.client.WebClient;

import com.web.curation.exception.TugetherException;
import com.web.curation.oauth.dto.GoogleTokenResponseDto;
import com.web.curation.oauth.properties.GoogleProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GoogleClient {

	private final WebClient accessWebClient;
	
	private final GoogleProperties googleProperties;
	
	// 생성자
	public GoogleClient(GoogleProperties googleProperties, WebClient.Builder webClientBuilder) {
		this.googleProperties = googleProperties;
		this.accessWebClient = webClientBuilder.baseUrl(googleProperties.getAccess()).build();
	}
	
	// 최초로그인 - 액세스토큰 요청 
	public GoogleTokenResponseDto getAccessTokenForLogin(String code) {
		try {
			return (GoogleTokenResponseDto) accessWebClient.get()
					.uri(uriBuilder-> uriBuilder.path("/v13.0/oauth/access_token")
                            .queryParam("client_id", googleProperties.getAppId())
                            .queryParam("client_secret", googleProperties.getSecret())
                            .queryParam("code", code)
                            .queryParam("redirect_uri", googleProperties.getLoginRedirectUri())
                            .build());
		} catch(RestClientException e) {
			throw new TugetherException();
		}
	}
}
