package com.web.curation.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import com.web.curation.exception.InvalidResponseCodeException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

	SUCCESS(HttpStatus.OK, 200, "OK"),
	USER_ALREADY_EXIST(HttpStatus.CONFLICT, 409, "등록된 사용자 입니다."),
	RESPONSE_CODE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, 999, "확인되지 않은 오류가 발생하였습니다.");

	private final HttpStatus httpStatus;

	private final int code;

	private final String message;

	public static ResponseCode getCode(int code) {
		return Arrays.stream(values()).findFirst().orElseThrow(InvalidResponseCodeException::new);
	}

}
