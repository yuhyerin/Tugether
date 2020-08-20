package com.web.curation.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	
	@Query(value="select distinct * from `profile` p where p.nickname like concat('%',:keyword,'%') and p.email!=:email", nativeQuery = true)
	List<Profile> findUserByNickname(String keyword, String email);
	
	@Query(value="select email from profile p where p.nickname like  CONCAT('%',:nickname,'%')")
	List<String> findEmailByNickname(String nickname);

	@Query(value="update profile set nickname= :nickname , profile_photo= :profile_photo where email= :email",nativeQuery=true)
	void updateProfilewithImage(String email, String nickname, String profile_photo);
	
	@Query(value="update profile set nickname= :nickname where email= :email",nativeQuery=true)
	void updateProfile(String email, String nickname);

	@Query(value = "select profile_photo from profile p where p.email=:email", nativeQuery=true)
	String findProfilePhoto(String email);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update profile p set p.article_cnt = p.article_cnt + 1 where p.email = :email",nativeQuery = true)
	void countPlusArticleCnt(@Param("email")String email);
	
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update profile p set p.article_cnt = p.article_cnt - 1 where p.email = :email",nativeQuery = true)
	void countMinusArticleCnt(@Param("email")String email);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update profile p set p.following_cnt = p.following_cnt + 1 where p.email = :from_email",nativeQuery = true)
	void countPlusFollowingCnt(@Param("from_email")String from_email);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update profile p set p.follower_cnt = p.follower_cnt + 1 where p.email = :to_email",nativeQuery = true)
	void countPlusFollowerCnt(@Param("to_email")String to_email);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update profile p set p.following_cnt = p.following_cnt - 1 where p.email = :from_email",nativeQuery = true)
	void countMinusFollowingCnt(@Param("from_email")String from_email);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update profile p set p.follower_cnt = p.follower_cnt - 1 where p.email = :to_email",nativeQuery = true)
	void countMinusFollowerCnt(@Param("to_email")String to_email);
}
