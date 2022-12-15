package com.web.curation.exception;

import com.web.curation.common.ResponseCode;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TugetherException extends RuntimeException {

	private static final long serialVersionUID = -6110005653362559998L;
	
	private String message;

	public TugetherException() {
		super();
	}

	public TugetherException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.message = responseCode.getMessage();
	}
	
	public TugetherException(String message) {
		super(message);
		this.message = message;
	}

}
