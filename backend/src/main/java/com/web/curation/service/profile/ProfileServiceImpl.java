package com.web.curation.service.profile;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.profile.Profile;
import com.web.curation.repo.FavtagRepo;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.repo.TagRepo;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	private ProfileRepo profileRepo;
	
	@Autowired
	private TagRepo tagRepo;
	
	@Autowired
	private FavtagRepo favtagRepo;
	
	// 프로필 조회 
	@Override
	public Profile getProfile(String email) {
		return profileRepo.findProfileByEmail(email);
	}

	// 프로필 변경 
	@Override
	public void updateProfile(String email, String nickname, String profile_photo) {
		profileRepo.updateProfile(email, nickname, profile_photo);
	}

	// 이전 관심태그 지우기
	@Override
	public void resetFavtag(String email) {
		favtagRepo.deleteFavtag(email);
	}
	
	// 관심태그 변경
	@Override
	public void updateFavtag(String email, ArrayList<String> favtaglist) {
		
		
		
	}

	
	
}
