package com.web.curation.repo;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.tag.FavTag;

@Repository
@Transactional
public interface FavtagRepo extends JpaRepository<FavTag, String>{ //JpaRepository< 반환형, 조회할id형 >

	@Query(value="select * from favtag u where u.email=:email",nativeQuery=true)
	List<FavTag> findFavTagByEmail(String email);
	
	@Query(value="insert into favtag(email,tag_id) values(:email , :tag_id )", nativeQuery=true)
	void insertFavtagList(String email, int tag_id);
	
	@Query(value="select tag_id from favtag f where f.email=:email", nativeQuery = true)
	public List<Integer> findTagIdByEmail(String email);

	@Query(value="delete from favtag where email= :email", nativeQuery = true)
	void deleteFavtag(String email);
	
	@Query(value="select fav_cnt from favtag where email = :email and tag_id = :tag_id", nativeQuery = true)
	FavTag getFavTagCnt(String email, int tag_id);

	
}
