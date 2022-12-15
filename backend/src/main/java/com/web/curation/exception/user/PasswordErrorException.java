package com.web.curation.exception.user;

import com.web.curation.common.ResponseCode;
import com.web.curation.exception.TugetherException;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PasswordErrorException extends TugetherException{

	public PasswordErrorException() {
		super(ResponseCode.PASSWORD_ERROR);
	}

	public PasswordErrorException(ResponseCode responseCode) {
		super(responseCode);
	}
	
}
