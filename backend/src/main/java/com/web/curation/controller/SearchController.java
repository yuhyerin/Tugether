package com.web.curation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.FrontArticle;
import com.web.curation.dto.profile.Profile;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.search.SearchService;

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
@RequestMapping("tugether/search")
public class SearchController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private SearchService searchService;

	// 1. 태그기반 검색
	@GetMapping("/tag")
	@ApiOperation(value = "키워드 포함 태그이름 검색")
	public ResponseEntity<Map<String, Object>> searchByTag(@RequestParam String keyword, HttpServletRequest request) {
		System.out.println("GET mapping 키워드 포함 태그이름 검색 DROP DOWN");
		Map<String, Object> resultMap = new HashMap<String, Object>();
/*
		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
*/
		System.out.println("keyword : ?"+keyword);
		List<String> list = searchService.findTagNamesByTag(keyword);
		resultMap.put("searchList",list);
		System.out.println("result : "+list.toString());
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

	@PostMapping("/tag")
	@ApiOperation(value = "태그검색")	//검색버튼 클릭 시 
	public ResponseEntity<Map<String, Object>> searchByTagName(@RequestBody Map<String, Object> map,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
		
		String keyword = (String)map.get("keyword");
		List<FrontArticle> articles = searchService.findArticlesByTagName(email, keyword);
		resultMap.put("articles", articles);
		System.out.println("result : " + articles.toString());
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	} 
	
	// 2. 유저기반 검색
	@GetMapping("/user")
	@ApiOperation(value = "키워드 포함 유저닉네임 검색")
	public ResponseEntity<Map<String, Object>> searchByNickname(@RequestParam String keyword, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();

		List<Profile> list = searchService.findUserByNickname(keyword, email);
		resultMap.put("searchList",list);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}

//	@PostMapping("/user")
//	@ApiOperation(value = "유저검색")	//검색버튼 클릭 시 
//	public ResponseEntity<Map<String, Object>> searchByTagName(@RequestParam String keyword, HttpServletRequest request) {
//
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//		String token = request.getHeader("jwt-auth-token");
//		Jws<Claims> claims = jwtService.getDecodeToken(token);
//		Map<String, Object> Userinfo = new HashMap<String, Object>();
//		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
//		String email = Userinfo.get("email").toString();
//
//		List<FrontArticle> articles = searchService.findArticlesByTagName(email, keyword);
//		System.out.println("result : " + articles.toString());
//		resultMap.put("articles", articles);
//		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
//	} 
	
/*	
	@GetMapping("/tag")
	@ApiOperation(value="태그기반검색")
	public ResponseEntity<Map<String, Object>> searchByTagName(@RequestParam String keyword, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
		
		List<String> list = searchService.findTagNamesByTag(keyword);
		List<FrontArticle> articles = searchService.findByTagName(email, keyword);
		
		resultMap.put("searchList", list);
		resultMap.put("articles", articles);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	
	//2. 유저기반 검색
	@GetMapping("/user")
	@ApiOperation(value="유저기반검색")
	public ResponseEntity<Map<String, Object>> searchByUserName(@RequestParam String keyword, HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
		
		List<String> list = searchService.findNickNamesByNickname(keyword);
		List<FrontArticle> articles = searchService.findArticlesByNickname(email, keyword);
		
		resultMap.put("searchList", list);
		resultMap.put("articles", articles);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
	}
	*/
	
	
}
