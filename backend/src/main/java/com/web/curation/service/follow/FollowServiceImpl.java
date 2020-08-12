package com.web.curation.service.follow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.follow.Follow;
import com.web.curation.dto.profile.Profile;
import com.web.curation.repo.FollowingRepo;
import com.web.curation.repo.ProfileRepo;

@Service
public class FollowServiceImpl implements FollowService{

	@Autowired
	private FollowingRepo followRepo;
	@Autowired
	private ProfileRepo profileRepo;
	
	/*
	 * private String profile_photo;
	private String nickname;
	private String email;	//얘네 셋은 이메일로 프로필테이블에서 가져오고
	private boolean follow;	// 얘는 팔로잉테이블에서 from_user랑 to_user로 데려옴
	 */
	
	@Override
	public List<Follow> findFollowerProfilesByEmail(String email) {
		List<Follow> followerList = new ArrayList<>();
		//1. following_tb에서 to_user=email인 from_user : 팔로워리스트
		List<String> followerIDs = followRepo.findFromUserByToUser(email);
		//2. profile_tb에서 followerID = email 인 프로필 중 nickname, email, profile_photo
		//3. follow = following_tb에서 from_user=email & to_user = followerID 가 있는 지
		for(int i=0;i<followerIDs.size();i++) {
			String followerID = followerIDs.get(i);
			boolean follow = followRepo.findOptionByFromAndTo(email, followerID).isPresent();
			Profile p = profileRepo.findProfileByEmail(followerID);
			Follow f = Follow.builder().profile_photo(p.getProfile_photo())
					.nickname(p.getNickname()).email(followerID)
					.follow(follow).build();
			followerList.add(f);
		}
		return followerList;
	}

	@Override
	public List<Follow> findFollowingProfilesByEmail(String email) {
		List<Follow> followingList = new ArrayList<>();
		// following_tb에서 from_user=email인 to_user : 팔로잉리스트
		List<String> followingIDs = followRepo.findToUserByFromUser(email);
		
		for(int i=0;i<followingIDs.size();i++) {
			String followingID = followingIDs.get(i);
			Profile p = profileRepo.findProfileByEmail(followingID);
			Follow f = Follow.builder().profile_photo(p.getProfile_photo())
					.nickname(p.getNickname()).email(followingID)
					.follow(true).build();
			followingList.add(f);
		}
		
		return followingList;
	}

}
