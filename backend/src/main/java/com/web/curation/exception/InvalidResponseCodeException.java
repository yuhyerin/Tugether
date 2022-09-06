package com.web.curation.exception;

import com.web.curation.common.ResponseCode;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class InvalidResponseCodeException extends TugetherException {

	public InvalidResponseCodeException() {
		super(ResponseCode.RESPONSE_CODE_NOT_FOUND);
	}

	public InvalidResponseCodeException(ResponseCode responseCode) {
		super(responseCode);
	}

}
