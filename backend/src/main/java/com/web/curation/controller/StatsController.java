package com.web.curation.controller;

import java.util.ArrayList;
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
import com.web.curation.dto.article.Article;
import com.web.curation.dto.tag.Tag;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.stats.StatsService;

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
public class StatsController {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private StatsService statsService;

	/** 가장 많이 검색된 태그 5개 */
	@GetMapping("/searchstats")
	public ResponseEntity<Map<String, Object>> searchstats(HttpServletRequest request) {

		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();

		// 많이 검색된 태그들
		ArrayList<Tag> topsearchtags = statsService.getTopSearchKeyword();
		resultMap.put("topsearchtags", topsearchtags);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

	}

	/** 게시글에 가장 많이 달린 태그 5개 */
	@GetMapping("/articletagstats")
	public ResponseEntity<Map<String, Object>> articletagstats(HttpServletRequest request) {

		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();

		// 게시글에 가장 많이달린 태그들 
		ArrayList<Tag> toparticletags = statsService.getTopArticleTag();
		resultMap.put("toparticletags", toparticletags);

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

	}

	/** 좋아요 가장많은 Article 3개 */
	@GetMapping("/likeystats")
	public ResponseEntity<Map<String, Object>> likeystats(HttpServletRequest request) {

		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();

		// 게시글에 가장 많이달린 태그들 
		List<Article> toplikeyarticles = statsService.getTopLikeyArticle(email);
		resultMap.put("toplikeyarticles", toplikeyarticles);
		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

	}

}
