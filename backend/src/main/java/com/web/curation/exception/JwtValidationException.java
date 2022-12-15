package com.web.curation.exception;

import com.web.curation.common.ResponseCode;

public class JwtValidationException extends TugetherException{

    private static final long serialVersionUID = -7385075540926150114L;

    public JwtValidationException() {
        super(ResponseCode.NOT_INVALID_TOKEN);
    }

    public JwtValidationException(ResponseCode responseCode) {
        super(responseCode);
    }

}
