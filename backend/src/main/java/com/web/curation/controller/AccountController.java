package com.web.curation.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.account.AuthenticationRequest;
import com.web.curation.dto.account.AuthenticationResponse;
import com.web.curation.dto.account.User;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.dto.account.User;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.account.FindService;
import com.web.curation.service.account.LoginService;
import com.web.curation.service.account.SignupService;


import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.account.User;
import com.web.curation.service.account.SignupService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
    private SignupService signupService;
    @Autowired
	private JwtService jwtService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private FindService findService;

    
    @GetMapping("/signup/{email}")
    @ApiOperation(value = "이메일 유효성 체크")
    public Object checkEmail(@PathVariable String email) throws MessagingException{
    	Map<String, Object> resultMap = new HashMap<>();
    	//1. 중복체크
    	ResponseEntity<Object> res = signupService.checkEmail(email);
    	BasicResponse result = (BasicResponse) res.getBody();
    	if("success".equals(result.data.toString())) {	//중복된 정보가 없으면  2.유효성 검사
    		String message = findService.sendTest(email);	//인증번호를 전송해서
    		resultMap.put("data","success");
    		resultMap.put("message", message);	// 프론트에 보낼 인증번호
    	}
    	ResponseEntity<Map<String, Object>> response = new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    	return response;
    }

    @PostMapping("/signup")
    @ApiOperation(value = "가입하기")
    public Object signup(@Valid @RequestBody User request) {

        return signupService.save(request);
    }
    
    @PostMapping("/signin")
    public ResponseEntity<Map<String,Object>> signin(@RequestBody AuthenticationRequest user, HttpServletResponse response) {
    	
    	Map<String, Object> resultMap = new HashMap<>();
    	HttpStatus status = null;
    	try {
    		AuthenticationResponse loginUser = loginService.login(user.getEmail(), user.getPassword());
    		System.out.println("로그인 할 수 있는 사용자 입니다! "+loginUser);
    		// 로그인 성공 했다면, 토큰을 생성한다.
    		String token = jwtService.create(loginUser);
    		response.setHeader("jwt-auth-token", token);
    		resultMap.put("status", true);
    		resultMap.put("data", loginUser);
    		status = HttpStatus.ACCEPTED;
    	}catch(RuntimeException e) {
    		System.out.println(e.getMessage());
    		resultMap.put("message", e.getMessage());
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}
    	
    	return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }
    
    @PostMapping("/info")
    public ResponseEntity<Map<String,Object>> getInfo(@RequestBody AuthenticationRequest user, HttpServletRequest request) {
    	Map<String, Object> resultMap = new HashMap<String, Object>();
    	HttpStatus status = null;
    	
    	try {
    		// 사용자에게 전달할 정보 
    		String info = loginService.getServerInfo();
    		// 토큰에 담긴 정보도 전달.
//    		resultMap.putAll(jwtService.getDecodeToken(request.getHeader("jwt-auth-token")));
    		
    		resultMap.put("status", true);
    		resultMap.put("info", info);
    		resultMap.put("request_body", user);
    		status = HttpStatus.ACCEPTED;
    	}catch(RuntimeException e) {
    		System.out.println("정보조회 실패!!");
    		resultMap.put("message", e.getMessage());
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}
    	
    	return new ResponseEntity<Map<String,Object>>(resultMap, status);
    }

    @PostMapping("/changepw")
    @ApiOperation(value = "비밀번호 변경")
    public Object changePW(@RequestBody AuthenticationRequest user) throws MessagingException {
    	System.out.println(user.toString());
    	User u = findService.changePasswordByEmail(user.getEmail());
    	System.out.println(u.toString());
    	u.setPassword(user.getPassword());
    	u.setTemp(false);
    	return findService.changePW(u);
    }
    
    
    @GetMapping("/findpw/{email}")
    @ApiOperation(value = "비밀번호 찾기")
    public Object sendMail(@PathVariable String email) throws MessagingException {
    	return findService.findUserByEmail(email);
    }

}