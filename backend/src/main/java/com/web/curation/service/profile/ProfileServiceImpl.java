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
	private FavtagRepo favtagRepo;
	
	@Autowired
	private TagRepo tagRepo;
	
	// 프로필 조회 
	@Override
	public Profile getProfile(String email) {
		return profileRepo.findProfileByEmail(email);
	}


	// 프로필 변경 - 이미지 변경했을 때 
	@Override
	public void updateProfilewithImage(Profile profile) {
		String email = profile.getEmail();
		String nickname = profile.getNickname();
		String profile_photo = email+ profile.getProfile_photo();
		profileRepo.updateProfilewithImage(email, nickname, profile_photo);
	}
	
	// 프로필 변경 
	@Override
	public void updateProfile(Profile profile) {
		
		String email = profile.getEmail();
		String nickname = profile.getNickname();
		profileRepo.updateProfile(email, nickname);

	}

	// 이전 관심태그 지우기
	@Override
	public void resetFavtag(String email) {
		favtagRepo.deleteFavtag(email);
	}
	
	// 관심태그 변경
	@Override
	public void updateFavtag(String email, ArrayList<Integer> favtaglist) {
		
		for(int i=0; i<favtaglist.size();i++) {
			favtagRepo.insertFavtagList(email, favtaglist.get(i).intValue());
		}
		System.out.println("관심태그에 등록했습니다.");

		
	}

	
	
}
