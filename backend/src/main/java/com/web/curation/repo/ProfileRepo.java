package com.web.curation.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.profile.Profile;

@Repository
@Transactional
public interface ProfileRepo extends JpaRepository<Profile, String>{
	
	@Query(value="select * from profile p where p.email=:email",nativeQuery=true)
	Profile findProfileByEmail(String email);

	@Query(value="update profile set article_cnt = :article_cnt where email= :email ",nativeQuery=true)
	void countMyArticle(String email, int article_cnt);
	
	@Query(value="select nickname from profile p where p.email=:email", nativeQuery=true)
	String findNicknameByEmail(String email);
	
	@Query(value="select profile_photo from profile p where p.email=:email", nativeQuery=true)
	String findProfilePhotoByEmail(String email);
}
