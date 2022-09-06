package com.web.curation.exception;

import com.web.curation.common.ResponseCode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(callSuper = true)
public class TugetherException extends RuntimeException {

	private ResponseCode responseCode;

	public TugetherException() {
		super();
	}

	public TugetherException(ResponseCode responseCode) {
		super(responseCode.getMessage());
		this.responseCode = responseCode;
	}

}
