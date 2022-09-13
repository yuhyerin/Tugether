package com.web.curation.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import lombok.Getter;

@Getter
public class TugetherResponse<T> {

//	private static final String SUCCESS = "success";
//    private static final String FAIL = "fail";
//    private static final String ERROR = "error";
	private static final boolean SUCCESS = true;
	private static final boolean FAIL = false;
	private static final boolean ERROR = false;

    /** 정상(success)
     * 예외(error)
     * 오류(fail)
     * 중 한 값을 갖는다. */
    private boolean status;
    
    /** 정상(success)의 경우 실제 전송될 데이터
     * 오류(fail)의 경우 유효성 검증에 실패한 데이터 */
    private T data;
    
    /** 예외(error)의 경우 예외 메세지를 응답한다.*/
    private String message;

    public static <T> TugetherResponse<T> createSuccess(T data) {
        return new TugetherResponse<>(SUCCESS, data, null);
    }

    public static TugetherResponse<?> createSuccessWithNoContent() {
        return new TugetherResponse<>(SUCCESS, null, null);
    }

    // Hibernate Validator에 의해 유효하지 않은 데이터로 인해 API 호출이 거부될때 반환
    public static TugetherResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new TugetherResponse<>(FAIL, errors, null);
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static TugetherResponse<?> createError(String message) {
        return new TugetherResponse<>(ERROR, null, message);
    }

    private TugetherResponse(boolean status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
