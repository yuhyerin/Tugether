package com.web.curation.controller;

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

import com.web.curation.dto.BasicResponse;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.scrap.ScrapService;

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
public class ScrapController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ScrapService scrapService;
	
	@ApiOperation(value = "스크랩 취소하기")
	@PostMapping("/scrapdelete")
	public ResponseEntity<Map<String, Object>> deleteArticle(
			@RequestBody Map<String, String> map,
			HttpServletRequest request) {

		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString(); // 이메일
		int article_id = Integer.parseInt(map.get("article_id"));
		
		/** scrap테이블에서 삭제 */
		scrapService.deleteScrapByArticleIdAndEmail(email, article_id);
		status = HttpStatus.OK;
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

}
