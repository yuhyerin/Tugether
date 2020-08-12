package com.web.curation.service.follow;

import java.util.List;

import com.web.curation.dto.follow.Follow;

public interface FollowService {
	
//	public List<Follow> findProfilesByEmail(String email);
	List<Follow> findFollowerProfilesByEmail(String email);
	List<Follow> findFollowingProfilesByEmail(String email);

}
