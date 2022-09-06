package com.web.curation.exception.user;

import com.web.curation.common.ResponseCode;
import com.web.curation.exception.TugetherException;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class UserAlreadyExist extends TugetherException {

	public UserAlreadyExist() {
		super(ResponseCode.USER_ALREADY_EXIST);
	}

	public UserAlreadyExist(ResponseCode responseCode) {
		super(responseCode);
	}
}
