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
import com.web.curation.dto.follow.Follow;
import com.web.curation.dto.profile.Profile;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.follow.FollowService;
import com.web.curation.service.profile.ProfileService;

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
@RequestMapping("/tugether/mypage")
public class FollowController {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private FollowService followService;
	@GetMapping("/followList")
	public ResponseEntity<Map<String,Object>> followList(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String email = Userinfo.get("email").toString();
		
		Profile user = profileService.getProfile(email);
		resultMap.put("profile", user);
		//팔로잉
		int following_cnt = user.getFollowing_cnt();
		resultMap.put("following_cnt", following_cnt);
		List<Follow> followingList = followService.findFollowingProfilesByEmail(email);
		resultMap.put("followingList", followingList);
		//팔로워
		int follower_cnt = user.getFollower_cnt();
		resultMap.put("follower_cnt", follower_cnt);
		List<Follow> followerList = followService.findFollowerProfilesByEmail(email);
		resultMap.put("followerList", followerList);

		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	
	@PostMapping("/followapply")
	public ResponseEntity<Map<String,Object>> followApply(@RequestBody Map<String, Object> map, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String from_email = Userinfo.get("email").toString();
		String to_email = map.get("email").toString();
		// 1. Follow테이블에 데이터 추가 
		// 2. from_email 유저의 Profile테이블에서 following_cnt +1 
		// 3. to_email 유저의 Profile테이블에서 follower_cnt + 1
		followService.addFollow(from_email, to_email);
		
		
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	
	@PostMapping("/followcancel")
	public ResponseEntity<Map<String,Object>> followCancel(@RequestBody Map<String, Object> map, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String token = request.getHeader("jwt-auth-token");	//토큰 가져와서
		Jws<Claims> claims = jwtService.getDecodeToken(token);	//복호화해서
		Map<String, Object> Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
		String from_email = Userinfo.get("email").toString();
		String to_email = map.get("email").toString();
		// 1. Follow테이블에 데이터 삭제 
		// 2. from_email 유저의 Profile테이블에서 following_cnt +1
		// 3. to_email 유저의 Profile테이블에서 follower_cnt + 1
		followService.deleteFollow(from_email, to_email);
		
		return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	}
	

}
