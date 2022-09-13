package com.web.curation.exception;

import com.web.curation.common.ResponseCode;

public class MailSendException extends TugetherException{
	
	private static final long serialVersionUID = -7385075540926150114L;

	public MailSendException() {
		super(ResponseCode.MAIL_SEND_FAIL);
	}

	public MailSendException(ResponseCode responseCode) {
		super(responseCode);
	}

}
