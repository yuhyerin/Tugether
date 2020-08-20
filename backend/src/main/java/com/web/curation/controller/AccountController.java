package com.web.curation.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.account.AuthenticationRequest;
import com.web.curation.dto.account.AuthenticationResponse;
import com.web.curation.dto.account.User;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.account.FindService;
import com.web.curation.service.account.LoginService;
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

	@Value("${CLIENT_ID}")
	private String CLIENT_ID;

	@GetMapping("/signup/{email}")
	@ApiOperation(value = "이메일 유효성 체크")
	public Object checkEmail(@PathVariable String email) throws MessagingException {
		Map<String, Object> resultMap = new HashMap<>();
		// 1. 중복체크
		ResponseEntity<Object> res = signupService.checkEmail(email);
		BasicResponse result = (BasicResponse) res.getBody();
		if ("success".equals(result.data.toString())) { // 중복된 정보가 없으면 2.유효성 검사
			String message = findService.sendTest(email); // 인증번호를 전송해서
			resultMap.put("data", "success");
			resultMap.put("message", message); // 프론트에 보낼 인증번호
		}
		ResponseEntity<Map<String, Object>> response = new ResponseEntity<Map<String, Object>>(resultMap,
				HttpStatus.OK);
		return response;
	}

	@PostMapping("/signup")
	@ApiOperation(value = "가입하기")
	public Object signup(@Valid @RequestBody User request) {

		return signupService.save(request);
	}

	@PostMapping("/signin")
	public ResponseEntity<Map<String, Object>> signin(@RequestBody AuthenticationRequest user,
			HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		// 로그인한 아이디가 구글연동 회원인가? 
		if(loginService.isGoogle(user.getEmail())) {
			resultMap.put("isgoogle", true);
			status = HttpStatus.ACCEPTED;
			return new ResponseEntity<Map<String, Object>>(resultMap, status);
		}else {
			System.out.println("소셜로그인 회원 아닙니다.");
		}
		try {
			AuthenticationResponse loginUser = loginService.login(user.getEmail(), user.getPassword());
			System.out.println("로그인 할 수 있는 사용자 입니다! " + loginUser);
			// 로그인 성공 했다면, 토큰을 생성한다.
			String token = jwtService.create(loginUser);
			response.setHeader("jwt-auth-token", token);
			resultMap.put("status", true);
			resultMap.put("data", loginUser);
			status = HttpStatus.ACCEPTED;
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@PostMapping("/googlesignin")
	public ResponseEntity<Map<String, Object>> googlesignin(@RequestBody Map<String, Object> map,
			HttpServletResponse response) {

		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			NetHttpTransport transport = new NetHttpTransport();
			JsonFactory jsonFactory = new GsonFactory();
			String googletoken = map.get("googletoken").toString().replaceAll("\"", "");
			System.out.println(googletoken);
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
					.setAudience(Collections.singletonList(CLIENT_ID)).build();

			GoogleIdToken idToken = null;
			try {
				idToken = verifier.verify(googletoken);
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (idToken != null) {// 토큰이 유효할 경우
				Payload payload = idToken.getPayload();

				// Print user identifier
				String userId = payload.getSubject();

				// Get profile information from payload
				String email = payload.getEmail();
				boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
				String nickname = (String) payload.get("name");
				String pictureUrl = (String) payload.get("picture");
				String locale = (String) payload.get("locale");
				String familyName = (String) payload.get("family_name");
				String givenName = (String) payload.get("given_name");
				
				/**
				 * 1. DB에서 해당 이메일이 있는지 없는지 조회한다. 
				 * 2. 없으면 => 회원가입
				 * 3. 있으면 => 토큰생성해서 리턴
				 */
				User tmpuser = loginService.isPresentEmail(email);
				if(tmpuser==null) { //존재하지 않으면 회원가입
//					User newuser = User.builder().email(email).password("임시비밀번호").nickname(nickname).birth_year(1995).gender('M').temp(true).build();
					User newuser = User.builder().email(email).nickname(nickname).temp(true).build();
					signupService.save(newuser);
				}
				// 이미 존재하는 회원이면
				User existuser= loginService.isPresentEmail(email);
				
				try {
					AuthenticationResponse loginUser = loginService.login(existuser.getEmail(), null);
					loginUser.setTemp(false);
					System.out.println("로그인 할 수 있는 사용자 입니다! " + loginUser);
					// 로그인 성공 했다면, 토큰을 생성한다.
					String token = jwtService.create(loginUser);
					response.setHeader("jwt-auth-token", token);
					resultMap.put("status", true);
					resultMap.put("data", loginUser);
					status = HttpStatus.ACCEPTED;
				} catch (RuntimeException e) {
					System.out.println(e.getMessage());
					resultMap.put("message", e.getMessage());
					status = HttpStatus.INTERNAL_SERVER_ERROR;
				}

			} else {
				System.out.println("구글토큰이 유효하지 않습니다!");
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<Map<String, Object>>(resultMap, status);

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