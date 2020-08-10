package com.web.curation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.notice.FrontNotice;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.notice.NoticeService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/tugether")
public class NoticeController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private NoticeService noticeService;
	
	@GetMapping("/notice")
	@ApiOperation(value="알림리스트띄우기")
	public ResponseEntity<Map<String, Object>> noticeList(HttpServletRequest request) {

		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
//		System.out.println("controller에 들어온 이메일 : "+email);
		List<FrontNotice> notices = noticeService.findNotice(email);
		
		resultMap.put("notices", notices);
		System.out.println("result : "+notices.toString());
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

	}
	
	

}
