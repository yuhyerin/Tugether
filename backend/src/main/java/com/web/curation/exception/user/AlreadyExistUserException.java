package com.web.curation.exception.user;

import com.web.curation.common.ResponseCode;
import com.web.curation.exception.TugetherException;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class AlreadyExistUserException extends TugetherException {

	public AlreadyExistUserException() {
		super(ResponseCode.ALREADY_EXIST_USER);
	}

	public AlreadyExistUserException(ResponseCode responseCode) {
		super(responseCode);
	}
}
