package com.web.curation.service.follow;

import java.util.List;

import com.web.curation.dto.follow.Follow;

public interface FollowService {
	
//	public List<Follow> findProfilesByEmail(String email);
	List<Follow> findFollowerProfilesByEmail(String email);
	List<Follow> findFollowingProfilesByEmail(String email);
	// Follow테이블에 데이터 추가& Profile 업데이트 
	void addFollow(String from_email, String to_email);
	// Follow테이블에서 데이터 삭제 & Profile 업데이트 
	void deleteFollow(String from_email, String to_email);

}
