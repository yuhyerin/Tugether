package com.web.curation.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.tag.Tag;

@Repository
public interface TagRepo extends JpaRepository<Tag, String>{
	
	@Query(value="select tag_name from tag t where t.tag_id=:tag_id", nativeQuery = true)
	public String findTagNameByTagId(int tag_id);
	
	@Query(value="select tag_id from tag t where t.tag_name = :tag_name",nativeQuery = true )
	public int findTagIdByTagName(String tag_name);

	@Query(value="insert into tag(tag_name, fav_cnt) values(:tagname , :fav_cnt )",nativeQuery = true)
	public void addTag(String tagname, int fav_cnt);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value="update tag t set t.fav_cnt = t.fav_cnt + 1 where t.tag_id = :tag_id",nativeQuery = true)
	public void updateFav_cnt(@Param("tag_id")int tag_id);
	

}
