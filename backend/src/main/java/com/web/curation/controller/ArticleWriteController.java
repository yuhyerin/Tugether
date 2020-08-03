package com.web.curation.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.multipart.MultipartFile;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.Article;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.articlewrite.ArticleWriteService;

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
public class ArticleWriteController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private ArticleWriteService articleService;
	
	@PostMapping("/articlewrite")
    public ResponseEntity<Map<String,Object>> addFavtag(@RequestBody Map<String, Object> map, HttpServletRequest request) {
    	
//		ArrayList<Integer> taglist = (ArrayList)map.get("taglist");
//		System.out.println(taglist.toString());
//		
//		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
//		
		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString(); //이메일 
		String writer = Userinfo.get("nickname").toString(); //닉네임 
		String content = map.get("content").toString(); //글내용
		String link = map.get("link").toString();

		try {
			
			String baseDir ="C:\\tugetherimg";
        	MultipartFile files = (MultipartFile)map.get("file"); // 파일 받아오기 
        	String fileName = files.getOriginalFilename();
			String filePath = baseDir + "\\" + fileName;
			files.transferTo(new File(filePath));
			
			Article article = new Article();
			article.setEmail(email);
			article.setWriter(writer);
			article.setImage(filePath);
			article.setContent(content);
			article.setLink(link);
			articleService.addArticle(article);
			resultMap.put("status", true);
    		status = HttpStatus.OK;
    		System.out.println("입력한 태그를 디비에 넣었습니다!!!!! ");
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("게시글 등록 실패 ");
    		resultMap.put("message", e.getMessage());
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
		
    }
	

}
