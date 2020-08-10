package com.web.curation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.account.User;
import com.web.curation.dto.article.Article;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.account.FindService;
import com.web.curation.service.mypage.MyPageService;

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
public class MyPageController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private MyPageService myPageService;
	@Autowired
	private FindService findService;
	
	@GetMapping("/profile/articles")
	@ApiOperation(value = "프로필 게시글")
	public ResponseEntity<Map<String,Object>> getProfile(HttpServletRequest request) {
	
			String token = request.getHeader("jwt-auth-token");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Jws<Claims> claims = jwtService.getDecodeToken(token);
			Map<String, Object> Userinfo = new HashMap<String, Object>();
			Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
			String email = Userinfo.get("email").toString();
			//1. 내 게시글 가져오기
			List<Article> articles = myPageService.findArticles(email); 
			System.out.println("articles : "+articles.toString());
			//2. 스크랩한 게시글 가져오기
			List<Article> scraps = myPageService.findScraps(email);
			System.out.println("scraps : "+scraps.toString());
			
			resultMap.put("articles", articles);
			resultMap.put("scraps", scraps);
			
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	
	}
	
	@GetMapping("/changepw")
	@ApiOperation(value="비밀번호확인")
	public ResponseEntity<Map<String,Object>> checkPW(HttpServletRequest request) {
		
		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
		String password = request.getHeader("password");
		boolean flag = findService.checkPW(email, password);
		resultMap.put("status", true);
		
		resultMap.put("data", flag ? "success" : "fail");
		
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);

}
	
	@PostMapping("/changepw")
    @ApiOperation(value = "비밀번호 변경")
    public ResponseEntity<Map<String,Object>> changePW(@RequestBody Map<String, Object> map, HttpServletRequest request) throws MessagingException {
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();	//email 가져올거임
		String password = (String)map.get("password");
    	User u = findService.changePasswordByEmail(email);
    	u.setPassword(password);
    	u.setTemp(false);
    	return findService.changePW(u);
    }

}
