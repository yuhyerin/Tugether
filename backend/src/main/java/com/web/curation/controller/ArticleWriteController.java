package com.web.curation.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.Article;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.articlewrite.ArticleWriteService;
import com.web.curation.service.profile.ProfileService;

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
public class ArticleWriteController {

	@Value("${window.article.upload.directory}")
//	@Value("${ubuntu.article.upload.directory}")
	String upload_FILE_PATH;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private ArticleWriteService articleWriteService;

	@Autowired
	private ProfileService profileService;
	
	/**
	 * 게시글 작성 시 해줘야 할꺼 
	 * 1. article테이블에 삽입 
	 * 2. tag테이블에 tag추가하고 article_cnt 하나증가 & articletag 테이블에 추가 
	 * 3. profile테이블에 article_cnt 하나증가
	 */
	@ApiOperation(value = "게시글 작성")
	@PostMapping("/articlewrite")
	public ResponseEntity<Map<String, Object>> addArticle(
			@RequestParam(name = "articleimg", required = false) MultipartFile mFile,
			@RequestParam("contents") String contents, 
			@RequestParam("link") String link,
			@RequestParam("taglist") ArrayList<String> taglist, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString(); // 이메일
		String writer = Userinfo.get("nickname").toString(); // 닉네임
		int article_id;
		Article article = new Article();
		article.setEmail(email);
		article.setWriter(writer);
		article.setContent(contents);
		article.setLink(link);
		article.setLike_cnt(0);
		article.setScrap_cnt(0);
		
		if (mFile == null) {
			System.out.println("ArticleWrite - No image");
			/** 1. article테이블에 article 추가 - 이미지X */
			article_id = articleWriteService.addArticle(article);
			
		} else {
			System.out.println("ArticleWrite - Add image");
			String articleimg = mFile.getOriginalFilename();
			article.setImage(email+articleimg);
			/** 1. article테이블에 article 추가 - 이미지O */
			article_id = articleWriteService.addArticlewithImage(article);
			// 이미지 파일 업로드
			try {
				// 파일업로드 할때 => 경로 + (작성자 이메일 + 파일명)
				mFile.transferTo(new File(upload_FILE_PATH + email + articleimg));
				System.out.println("파일을 업로드 했습니다.");

			} catch (IllegalStateException | IOException e) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				e.printStackTrace();
			}
		}
		
		/** 2. articletag 테이블에 등록한 태그들 추가 (없는태그는 tag테이블에도 추가해줌, article_cnt도하나 증가) */
		articleWriteService.addArticleTag(article_id, taglist);

		/** 3. profile테이블에 article_cnt 하나 증가 */
		profileService.countPlusArticleCnt(email);
		status = HttpStatus.OK;
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

}
