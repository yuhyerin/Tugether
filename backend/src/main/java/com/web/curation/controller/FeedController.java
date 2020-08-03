package com.web.curation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.Article;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.feed.FeedService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/tugether")
public class FeedController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private FeedService feedService;

	// 1. 태그기반 피드
	// 유저테이블에서 email get -> favtag테이블에서 해당 이메일로 tag_id 찾아와
	// ArticleTag테이블에서 tag_id로 article_id들 리스트로 받아와
	// Article테이블에서 article_id로 article리스트 다 데려와
	
	// 2. 팔로우기반 피드
	// following테이블에서 from_user=email로 to_user 리스트 찾아와
	// article 테이블에서 uid = email인 List<article>로 다 가져가
	@PostMapping("/mainfeed")
	public ResponseEntity<Map<String,Object>> MainFeed(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		System.out.println("FeedController 입장");
		Map<String, Object> resultMap = new HashMap<String, Object>();	//리턴용
		HttpStatus status = null;	//너도 리턴용
		
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		System.out.println("token : "+token);
		
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		System.out.println("claims : "+claims.toString());
		System.out.println(claims.getBody().get("AuthenticationResponse"));	//얘는 디버깅
		
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		System.out.println("userinfo : "+Userinfo.toString());
		
		String email = Userinfo.get("email").toString();	//email 가져올거임
		System.out.println("email : "+email);
		
		List<Article> list = null;
		
		//1. 태그기반 / 팔로우기반 확인
		boolean tag = (boolean)map.get("tag");
		System.out.println("tag : "+tag);
//		//1. 태그기반
//		if(tag) {
//			list = feedService.findArticleListByTag(email);
//		} else {
//			//2. 팔로우기반
//			list = feedService.findArticleListByFollow(email);
//		}
//		resultMap.put("list", list);
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
	}
}
