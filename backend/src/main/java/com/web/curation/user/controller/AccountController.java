package com.web.curation.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.common.TugetherResponse;
import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.account.AuthenticationRequest;
import com.web.curation.entity.User;
import com.web.curation.exception.TugetherException;
import com.web.curation.oauth.dto.GoogleOAuthCallbackDto;
import com.web.curation.oauth.service.OAuthService;
import com.web.curation.user.dto.EmailCheckDto;
import com.web.curation.user.dto.FindPasswordDto;
import com.web.curation.user.dto.GoogleLoginRequestDto;
import com.web.curation.user.dto.GoogleLoginResponseDto;
import com.web.curation.user.dto.LoginDto;
import com.web.curation.user.dto.SignUpDto;
import com.web.curation.user.service.FindService;
import com.web.curation.user.service.LoginService;
import com.web.curation.user.service.SignupService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ApiResponses(value = {@ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class)})

@Slf4j
@CrossOrigin(origins = {"*"})
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final SignupService signupService;
    private final LoginService loginService;
    private final FindService findService;
    private final OAuthService oAuthService;

    @GetMapping("/accounts/{email}")
    @ApiOperation(value = "이메일 인증 요청")
    public TugetherResponse<EmailCheckDto> checkEmail(@PathVariable String email) {
        log.info("[Get] /accounts/{email} " + email);
        return TugetherResponse.createSuccess(signupService.checkEmail(email));
    }

    @PostMapping("/accounts")
    @ApiOperation(value = "가입하기")
    public TugetherResponse<SignUpDto> signup(@Valid @RequestBody User user) {
        log.info("[Post] /accounts ");
        return TugetherResponse.createSuccess(signupService.save(user));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<TugetherResponse<LoginDto>> login(@RequestBody User user) {
        log.info("[Post] /login ");
        LoginDto result = loginService.login(user);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, result.getJwtToken());
        return ResponseEntity.ok().headers(header).body(TugetherResponse.createSuccess(result));
    }

    @PostMapping("/login/google")
    @ApiOperation(value = "구글 연동 로그인")
    public ResponseEntity<TugetherResponse<GoogleLoginResponseDto>> googleLogin(@RequestBody GoogleLoginRequestDto user) {
        log.info("[Post] /login/google");
        GoogleLoginResponseDto result = loginService.googleLogin(user);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, result.getJwtToken());
        return ResponseEntity.ok().headers(header).body(TugetherResponse.createSuccess(result));
    }

    @PostMapping("/login/google/v2")
    @ApiOperation(value = "구글 연동 로그인")
    public ResponseEntity<TugetherResponse<GoogleLoginResponseDto>> googleLogin2(@RequestBody GoogleOAuthCallbackDto callbackDto) {
        log.info("[Post] /login/google/v2");
        if (Objects.isNull(callbackDto.getCode()))
            throw new TugetherException("구글 인증코드가 필요합니다.");
//		GoogleLoginResponseDto result = loginService.googleLogin2(oath2Dto);
        GoogleLoginResponseDto result = oAuthService.loginByGoogle(callbackDto);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.AUTHORIZATION, result.getJwtToken());
        return ResponseEntity.ok().headers(header).body(TugetherResponse.createSuccess(result));
    }

    @PostMapping("/info")
    public ResponseEntity<Map<String, Object>> getInfo(@RequestBody AuthenticationRequest user,
                                                       HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = null;

        try {
            // 토큰에 담긴 정보도 전달.
            resultMap.put("status", true);
            resultMap.put("request_body", user);
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            System.out.println("정보조회 실패!!");
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/changepw")
    @ApiOperation(value = "비밀번호 변경")
    public Object changePW(@RequestBody AuthenticationRequest user) throws MessagingException {
        System.out.println(user.toString());
        User u = findService.changePasswordByEmail(user.getEmail());
        System.out.println(u.toString());
//		u.setPassword(user.getPassword());
//		u.setTemp(false);
        return findService.changePW(u);
    }

    @GetMapping("/findpw/{email}")
    @ApiOperation(value = "비밀번호 찾기")
    public TugetherResponse<FindPasswordDto> sendMail(@PathVariable String email) throws MessagingException {
        return TugetherResponse.createSuccess(findService.findPassword(email));
    }

}