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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.feed.FeedService;

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
public class FeedController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private FeedService feedService;

	// 1. 태그기반 피드
	// front에서 email get -> favtag테이블에서 해당 이메일로 tag_id 찾아와
	// ArticleTag테이블에서 tag_id로 article_id들 리스트로 받아와
	// Article테이블에서 article_id로 article리스트 다 데려와
	// ArticleTag테이블에서 article_id로 List<tag_id> 가져와 => Tag테이블에서 tag_id로 tag_name 가져와
	
	// 2. 팔로우기반 피드
	// following테이블에서 from_user=email로 to_user 리스트 찾아와
	// article 테이블에서 uid = email인 List<article>로 다 가져가
	@PostMapping("/mainfeed")
	public ResponseEntity<Map<String,Object>> MainFeed(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();	//email 가져올거임
		List<FrontArticle> list = null;
		
//		1. 태그기반 / 팔로우기반 확인
		boolean tag = (boolean)map.get("tag");

		if(tag) {	// 1. 태그기반
			list = feedService.findArticleListByTag(email);
		} else {	// 2. 팔로우기반
			list = feedService.findArticleListByFollow(email);
		}
		resultMap.put("list", list);
		
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	
	@GetMapping("/mainfeed/like")
	@ApiOperation(value = "좋아요")
	public ResponseEntity<Map<String,Object>> like(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();

		int article_id = Integer.parseInt(request.getHeader("article_id"));
		// 좋아요 업데이트
		FrontArticle a = feedService.updateLike(article_id, email);
		resultMap.put("article",a);
		System.out.println("return : "+a.toString());
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	
	@GetMapping("/mainfeed/scrap")
	@ApiOperation(value = "스크랩여부")
	public ResponseEntity<Map<String,Object>> scrapCheck(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
		int article_id = Integer.parseInt(request.getHeader("article_id"));
		
		boolean flag = feedService.checkScrap(email, article_id);
		resultMap.put("scrapcheck", flag);
		System.out.println("return : "+flag);
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	
	@PostMapping("/mainfeed/scrap")
	@ApiOperation(value = "스크랩하기")
	public ResponseEntity<Map<String,Object>> scrap(@RequestBody Map<String, Object> map, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();	//email 가져올거임
		
		int article_id = (int)map.get("article_id");
//		feedService.scrap(email, article_id);
		FrontArticle a = feedService.scrap(email, article_id);
 		resultMap.put("article", a);
 		System.out.println("result : "+a.toString());
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	
	
}