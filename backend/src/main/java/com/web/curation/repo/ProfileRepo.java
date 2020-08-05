package com.web.curation.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.profile.Profile;

@Repository
@Transactional
public interface ProfileRepo extends JpaRepository<Profile, String>{
	
	@Query(value="select * from profile u where u.email=:email",nativeQuery=true)
	Profile findProfileByEmail(String email);
}
