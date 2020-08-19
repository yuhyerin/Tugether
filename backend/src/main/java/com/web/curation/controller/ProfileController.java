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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.profile.Profile;
import com.web.curation.jwt.service.JwtService;
import com.web.curation.service.articlewrite.ArticleUpdateService;
import com.web.curation.service.profile.ProfileService;
import com.web.curation.service.tag.FavtagService;
import com.web.curation.service.tag.TagService;

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
public class ProfileController {

	@Value("${window.profile.upload.directory}")
//	@Value("${ubuntu.profile.upload.directory}")
	String upload_FILE_PATH;
	
	@Autowired
	private ProfileService profileSerivce;
	@Autowired
	private TagService tagService;
	@Autowired
	private FavtagService favtagService;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private ArticleUpdateService articleUpdateService;
	

	@GetMapping("/profile")
	@ApiOperation(value = "회원의 프로필정보 가져오기 ")
	public ResponseEntity<Map<String,Object>> getProfile(HttpServletRequest request) {
	
			String token = request.getHeader("jwt-auth-token");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Jws<Claims> claims = jwtService.getDecodeToken(token);
			Map<String, Object> Userinfo = new HashMap<String, Object>();
			Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
			String email = Userinfo.get("email").toString();
			
			 //이메일 보내서 프로필 가져오기
			Profile profile = profileSerivce.getProfile(email);
			resultMap.put("profile", profile);
			System.out.println("프로필 전달 합니다!!!");
			
			 //이메일 보내서 관심태그 가져오기
			 ArrayList<String> favtaglist = favtagService.getFavtagList(email);
			 for(int i=0; i<favtaglist.size();i++) {
				 System.out.println(favtaglist.get(i));
			 }
			 resultMap.put("favtaglist", favtaglist);
			 System.out.println("관심태그 프론트로 전달했습니다.");

			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
	
	}
	
	/** 1. User 테이블에서 닉네임이 업데이트 된다.
	 *  2. Profile 테이블에서  닉네임, 프로필사진이 업데이트 된다.
	 *  3. Favtag 테이블에서 관심태그가 업데이트 된다. */
	@PostMapping("/profilechange")
	@ApiOperation(value = "회원의 프로필 수정하기 ")
	public ResponseEntity<Map<String,Object>> updateProfile(
			@RequestParam(name="profile_photo", required = false) MultipartFile mFile,
			@RequestParam("nickname") String nickname,
    		@RequestParam("taglist") ArrayList<String> favtaglist,
			HttpServletRequest request) {
	
			HttpStatus status = null;
			String token = request.getHeader("jwt-auth-token");
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Jws<Claims> claims = jwtService.getDecodeToken(token);
			Map<String, Object> Userinfo = new HashMap<String, Object>();
			Userinfo = (Map<String, Object>) claims.getBody().get("AuthenticationResponse");
			String email = Userinfo.get("email").toString();
			
			/** 1. User테이블에서 닉네임 업데이트 */
			profileSerivce.updateUserNickname(email, nickname);
			
			Profile profile = new Profile();
			profile.setEmail(email);
			profile.setNickname(nickname);
			
			/** 2. Article테이블에서 writer 업데이트  */
			articleUpdateService.updateArticleWriter(email,nickname);
			
			/** 3. Profile테이블 업데이트 */
			if(mFile==null) { // 프로필사진을 변경하지 않은 경우 
				// 이메일로 해당 유저 프로필 수정하기 
				profileSerivce.updateProfile(profile);
				
			}else { // 프로필사진을 변경한 경우
				String profile_photo = mFile.getOriginalFilename();
				profile.setProfile_photo(email+profile_photo);
				profileSerivce.updateProfilewithImage(profile);
				
				// 프로필사진 업로드
				try {
					// 파일업로드 할때 => 경로 + (작성자 이메일 + 파일명) 
					mFile.transferTo(new File(upload_FILE_PATH+email+profile_photo));
					status = HttpStatus.OK;
					System.out.println("프로필사진을 업로드 했습니다.");
					
				}catch(IllegalStateException | IOException e) {
					status = HttpStatus.INTERNAL_SERVER_ERROR;
					System.out.println("프로필사진을 업로드하지 못했습니다.");
					e.printStackTrace();
				}
				
			}

			// 이전 관심태그 지우기
			profileSerivce.resetFavtag(email);
					
			// 태그리스트 [ "고양이", "게임", "롤" ] 가 태그테이블에 있는지 검사해서, 없으면 추가하고 태그id를 얻어오기.
			ArrayList<Integer> favtagIdlist = tagService.updateFavtag(favtaglist);
			profileSerivce.updateFavtag(email, favtagIdlist);
			status = HttpStatus.OK;


			return new ResponseEntity<Map<String,Object>>(resultMap, status);
	
	}


}
