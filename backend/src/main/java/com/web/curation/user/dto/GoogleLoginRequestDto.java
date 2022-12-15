package com.web.curation.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoogleLoginRequestDto {
	
//	private String email; // 구글로그인 시도하는 시점에 이메일을 줄 수가 없음. 
	private String googletoken;
}
