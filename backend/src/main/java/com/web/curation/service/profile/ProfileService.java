package com.web.curation.service.profile;

import java.util.ArrayList;

import com.web.curation.dto.profile.Profile;

public interface ProfileService {

	// 해당 이메일의 프로필 조회하기 
	public Profile getProfile(String email);
	
	// 프로필 업데이트 - 이미지 변경 
	public void updateProfilewithImage(Profile profile);
	
	// 프로필 업데이트
	public void updateProfile(Profile profile);

	// 이전 관심태그 지우기 
	public void resetFavtag(String email);
	
	// 관심태그 수정하기
	public void updateFavtag(String email, ArrayList<Integer> favtaglist);

	// 게시글 추가 카운팅하기
	public void countPlusArticleCnt(String email);
	
	// 게시글 삭제 카운팅하기
	public void countMinusArticleCnt(String email);

	// User테이블에서 닉네임 변경하기
	public void updateUserNickname(String email, String nickname);

}
