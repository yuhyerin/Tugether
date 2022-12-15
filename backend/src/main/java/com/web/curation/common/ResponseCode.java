package com.web.curation.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import com.web.curation.entity.User;
import com.web.curation.exception.InvalidResponseCodeException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

	/** 200 */
	SUCCESS(HttpStatus.OK, 200, "OK"),
	
	/** 4xx */
	PASSWORD_ERROR(HttpStatus.UNAUTHORIZED, 44010, "비밀번호가 틀립니다."),
	NOT_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 44011, "유효한 토큰이 아닙니다."),
	NOT_EXIST_EMAIL(HttpStatus.NOT_FOUND, 44040,"존재하지 않는 이메일 입니다."),
	ALREADY_EXIST_USER(HttpStatus.CONFLICT, 44090, "등록된 사용자 입니다."),
	OTHER_PLATFORM_USER(HttpStatus.CONFLICT, 44091, "다른 플랫폼 연동 회원 입니다."),
	
	/** 5xx */
	MAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, 55000,"메일 발송 중 에러가 발생했습니다."),
	CIPHER_UTIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 55001,"비밀번호 암호화 중 에러가 발생했습니다."),
	
	
	/** 999 */
	RESPONSE_CODE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, 99999, "확인되지 않은 오류가 발생했습니다.");
	
	private static final User USER = null;

	private final HttpStatus httpStatus;

	private final int code;

	private final String message;

	public static ResponseCode getCode(int code) {
		return Arrays.stream(values()).findFirst().orElseThrow(InvalidResponseCodeException::new);
	}
	
}
