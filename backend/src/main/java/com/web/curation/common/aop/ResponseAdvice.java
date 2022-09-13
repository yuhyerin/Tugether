package com.web.curation.common.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.web.curation.common.ResponseCode;
import com.web.curation.common.TugetherResponse;
import com.web.curation.exception.MailSendException;
import com.web.curation.exception.TugetherException;
import com.web.curation.exception.user.UserAlreadyExist;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ResponseAdvice {
	
	/** 예상치못한 런타임에러 
	 * */
	@ExceptionHandler(RuntimeException.class)
	ResponseEntity<TugetherResponse<?>> handleRuntimeException(RuntimeException e){
        log.error("[" + e.getClass() + "] " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TugetherResponse.createError(ResponseCode.RESPONSE_CODE_NOT_FOUND.getMessage()));
	}
	
	@ExceptionHandler(TugetherException.class)
	ResponseEntity<TugetherResponse<?>> handleTugetherException(TugetherException e){
        log.error("[" + e.getClass() + "] " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TugetherResponse.createError(e.getMessage()));
	}
	
	/** ************* CheckedException  **************
	 * - 반드시 처리해줘야 하는 CheckedException 
	 * */
	
	/**
	 * MailSendException
	 * 메일발송 중 에러가 발생했습니다.
	 * */
	@ExceptionHandler(MailSendException.class)
    public ResponseEntity<TugetherResponse<?>> handleMailException(RuntimeException e) {
		log.error("["+ e.getStackTrace()[0].getClassName()+" : "+ e.getStackTrace()[0].getMethodName()+"] (" + e.getClass() + ") " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TugetherResponse.createError(e.getMessage()));
    }
	
	
	/** ************* UncheckedException ************** 
	 *  - UncheckedException 이라서 굳이 처리해 줄 필요는 없지만
	 *  - 커스텀 메세지를 전달해서 처리해 주고 싶을때 이곳에 작성하면 된다.
	 */
	
	/**
	 * UserAlreadyExist
	 * 이미 존재하는 사용자입니다.
	 * */
	@ExceptionHandler(UserAlreadyExist.class)
    public ResponseEntity<TugetherResponse<?>> handleUserAlreadyExist(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.OK).body(TugetherResponse.createError(e.getMessage()));
    }

}
