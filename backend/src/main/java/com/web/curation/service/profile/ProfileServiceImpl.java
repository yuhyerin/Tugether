package com.web.curation.service.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.profile.Profile;
import com.web.curation.repo.ProfileRepo;

@Service
public class ProfileServiceImpl implements ProfileService{

	@Autowired
	private ProfileRepo profileRepo;
	
	@Override
	public Profile getProfile(String email) {
		return profileRepo.findProfileByEmail(email);
	}

}
