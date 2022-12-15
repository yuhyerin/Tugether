package com.web.curation.exception.user;

import com.web.curation.common.ResponseCode;
import com.web.curation.exception.TugetherException;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class OtherPlatformLoginUserException extends TugetherException{
	
	public OtherPlatformLoginUserException() {
		super(ResponseCode.OTHER_PLATFORM_USER);
	}

	public OtherPlatformLoginUserException(ResponseCode responseCode) {
		super(responseCode);
	}
}
