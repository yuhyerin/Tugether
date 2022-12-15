package com.web.curation.exception.user;

import com.web.curation.common.ResponseCode;
import com.web.curation.exception.TugetherException;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class NotExistEmailException extends TugetherException {
	
	public NotExistEmailException() {
		super(ResponseCode.NOT_EXIST_EMAIL);
	}

	public NotExistEmailException(ResponseCode responseCode) {
		super(responseCode);
	}

}
