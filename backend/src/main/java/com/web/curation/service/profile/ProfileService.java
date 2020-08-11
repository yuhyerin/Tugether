package com.web.curation.service.profile;

import java.util.ArrayList;

import com.web.curation.dto.profile.Profile;

public interface ProfileService {

	// 해당 이메일의 프로필 조회하기 
	public Profile getProfile(String email);
	
	// 해당 이메일의 프로필 업데이트하기
	public void updateProfile(String email, String nickname, String profile_photo);

	// 이전 관심태그 지우기 
	public void resetFavtag(String email);
	
	// 관심태그 수정하기
	public void updateFavtag(String email, ArrayList<Integer> favtaglist);


	

}
