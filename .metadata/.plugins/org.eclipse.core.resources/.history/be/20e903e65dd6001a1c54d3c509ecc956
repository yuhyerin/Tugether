package com.web.curation.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.tag.FavTag;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.tag.FavtagService;

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
public class FavtagController {

	@Autowired
	private FavtagService favtagService;
	
	@Autowired
	private JwtService jwtService;
	
	// 사용자가 관심태그를 등록했는지 검사 
	@GetMapping("/checkfavtag")
	public ResponseEntity<Map<String,Object>> checkFavtag(HttpServletRequest request) {
			
		String token = request.getHeader("jwt-auth-token");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		System.out.println(claims.getBody().get("AuthenticationResponse"));
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
		String nickname =Userinfo.get("nickname").toString();
		System.out.println("이메일: " + email);
		System.out.println("닉네임: " + nickname + "님의 관심태그를 등록했는지 검사하는 중입니다.");
		if (favtagService.isSelectFavtag(email)) {
			System.out.println("관심태그를 선택한 회원입니다. ");
			resultMap.put("status", true);
			// status = true;
		} else {
			System.out.println("관심태그를 선택안한!! 회원입니다. ");
			resultMap.put("status", false);
			// status = false;
		}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);

	}
	
	// 사용자가 고른 관심태그 리스트를 받아온다. 
	@PostMapping("/favtag")
    public ResponseEntity<Map<String,Object>> addFavtag(@RequestBody Map<String, Object> map, HttpServletRequest request) {
    	
		ArrayList<Integer> taglist = (ArrayList)map.get("taglist");
		System.out.println(taglist.toString());
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		
		String token = request.getHeader("jwt-auth-token");
		Jws<Claims> claims = jwtService.getDecodeToken(token);
		System.out.println(claims.getBody().get("AuthenticationResponse"));
		Map<String, Object> Userinfo = new HashMap<String, Object>();
		Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
		
		System.out.println("로그인한 사용자의 토큰은??? "+ request.getHeader("jwt-auth-token"));
		System.out.println("보낸 관심태그 리스트중에 가장앞에꺼는? "+ taglist.get(0));
		
		try {
    		
			favtagService.addFavtag( email , taglist);
    		resultMap.put("status", true);
    		status = HttpStatus.OK;
    		System.out.println("입력한 태그를 디비에 넣었습니다!!!!! ");
    		
    	}catch(RuntimeException e) {
    		System.out.println("관심태그 등록 실패 ");
    		resultMap.put("message", e.getMessage());
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}
		
		return new ResponseEntity<Map<String,Object>>(resultMap, status);
		
    }
	
	@GetMapping("/favtest")
	public void addFavTest(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = null;
		ArrayList<Integer> favtaglist = new ArrayList<>();
		// 테스트로 1, 3 추가 
		favtaglist.add(3);
		favtaglist.add(5);
		
//		resultMap.putAll(jwtService.getDecodeToken(request.getHeader("jwt-auth-token")));
		System.out.println("로그인한 사용자의 토큰은??? "+ request.getHeader("jwt-auth-token"));
		System.out.println("보낸 관심태그 리스트중에 가장앞에꺼는? "+ favtaglist.get(0));
		try {
    		
			favtagService.addFavtag("hyerin77@naver.com", favtaglist);
//    		resultMap.putAll(jwtService.getDecodeToken(request.getHeader("jwt-auth-token")));
    		resultMap.put("status", true);
    		status = HttpStatus.ACCEPTED;
    	}catch(RuntimeException e) {
    		System.out.println("정보조회 실패!!");
    		resultMap.put("message", e.getMessage());
    		status = HttpStatus.INTERNAL_SERVER_ERROR;
    	}
		
	}
}
