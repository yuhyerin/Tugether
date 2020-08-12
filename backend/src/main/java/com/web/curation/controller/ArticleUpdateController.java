package com.web.curation.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.Article;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.articlewrite.ArticleUpdateService;
import com.web.curation.service.articlewrite.ArticleWriteService;

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
public class ArticleUpdateController {

//	@Value("${ubuntu.article.upload.directory}")
	@Value("${window.article.upload.directory}")
	String upload_FILE_PATH;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private ArticleWriteService articleWriteService;

	@Autowired
	private ArticleUpdateService articleUpdateService;

	@ApiOperation(value = "게시글 내용 가져오기")
	@GetMapping("/articleloading")
	public ResponseEntity<Map<String, Object>> getArticle(HttpServletRequest request) {

		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();

		// 게시글번호 보내서 내용 가져오기
		int article_id = Integer.parseInt(request.getHeader("article_id"));
		Article article = articleUpdateService.getArticle(article_id);
		resultMap.put("article", article);
		ArrayList<String> favtaglist = articleUpdateService.getArticleTag(article_id);
		resultMap.put("favtaglist", favtaglist);
		System.out.println("게시글 정보 준비 완료!!");

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

	}

	@ApiOperation(value = "게시글 수정하기")
	@PostMapping("/articleupdate")
	public ResponseEntity<Map<String, Object>> updateArticle(
			@RequestParam(name = "articleimg", required = false) MultipartFile mFile,
			@RequestParam("article_id") String article_id, 
			@RequestParam("contents") String contents,
			@RequestParam("link") String link, 
			@RequestParam("taglist") ArrayList<String> taglist,
			HttpServletRequest request) {

		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString(); // 이메일
		String writer = Userinfo.get("nickname").toString(); // 닉네임
		int articleid = Integer.parseInt(article_id);

		Article article = new Article();
		if (mFile == null) {
			System.out.println("이미지를 수정하지 않았습니다. ");
		} else {
			String articleimg = mFile.getOriginalFilename();
			article.setImage(email+articleimg);
			System.out.println("파일명: "+articleimg);
			// 이미지 파일 업로드
			try {
				// 파일업로드 할때 => 경로 + (작성자 이메일 + 파일명)
				mFile.transferTo(new File(upload_FILE_PATH + email + articleimg));
				status = HttpStatus.OK;
				System.out.println("파일을 업로드 했습니다.");

			} catch (IllegalStateException | IOException e) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				e.printStackTrace();
			}
		}

		article.setArticle_id(articleid);
		article.setContent(contents);
		article.setLink(link);
		article.setLike_cnt(0);
		article.setScrap_cnt(0);
		articleUpdateService.updateArticle(article);
		System.out.println("Controller - 게시글을 업데이트 합니다.");
		
		// 태그리스트 한번 날리고,
		articleUpdateService.resetArticleTag(articleid);
		System.out.println("기존 게시글 태그리스트를 제거 했습니다.");

		// 등록한 태그 리스트를 넘겨주면 받아서 => ArticleTag에 추가 하기 !!!
		articleWriteService.addArticleTag(articleid, taglist);
		System.out.println("새로운 게시글 태그리스트를 등록 했습니다.");
		status = HttpStatus.OK;
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

}
