package com.web.curation.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.Article;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.articlewrite.ArticleWriteService;
import com.web.curation.service.fileupload.FileUploadService;

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
	
//	@Value("${window.article.upload.directory}")
	@Value("${ubuntu.article.upload.directory}")
	String upload_FILE_PATH;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ArticleWriteService articleWriteService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@ApiOperation(value = "게시글 작성")
	@PostMapping("/articlewrite")
    public ResponseEntity<Map<String,Object>> addArticle( @RequestParam("articleimg") MultipartFile mFile, 
    		@RequestParam("contents") String contents,
    		@RequestParam("link") String link,
    		@RequestParam("taglist") ArrayList<String> taglist,
    		HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString(); //이메일 
		String writer = Userinfo.get("nickname").toString(); //닉네임 
		
		// 사진이름 = 작성자이메일 + 파일명 
		String articleimg = email+mFile.getOriginalFilename();
		Article article = new Article();
		article.setEmail(email);
		article.setWriter(writer);
		article.setContent(contents);
		article.setImage(articleimg);
		article.setLink(link);
		article.setLike_cnt(0);
		article.setScrap_cnt(0);
		
		for(int i=0; i<taglist.size(); i++) {
			System.out.println(taglist.get(i));
		}
		
		// DB에 저장할때 이미지는 이메일+파일명 만 !!!
		int article_id = articleWriteService.addArticle(article);
		
		System.out.println("DB에 글을 insert 했습니다.");
		System.out.println("입력한 게시글의 article_id는 "+article_id);
		
		
		// 등록한 태그 리스트를 넘겨주면 받아서 => ArticleTag에 추가 하기 !!! 
		articleWriteService.addArticleTag(article_id, taglist);
		System.out.println("DB에 게시글의 태그를 insert 했습니다.");
		
		// 이미지 파일 업로드 
		try {
			// 파일업로드 할때 => 경로 + (작성자 이메일 + 파일명) 
			mFile.transferTo(new File(upload_FILE_PATH+articleimg));
			status = HttpStatus.OK;
			System.out.println("파일을 업로드 했습니다.");
			
		}catch(IllegalStateException | IOException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			e.printStackTrace();
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
		
    }

}