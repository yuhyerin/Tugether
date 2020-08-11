package com.web.curation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.tag.Tag;

@Repository
public interface TagRepo extends JpaRepository<Tag, String>{
	
	@Query(value="select tag_name from tag t where t.tag_id=:tag_id", nativeQuery = true)
	public String findTagNameByTagId(int tag_id);
	
	//키워드를 포함한 모든 태그이름 찾기
	@Query(value="select tag_name from tag t where t.tag_name like CONCAT('%',:keyword,'%')", nativeQuery=true)
	public List<String> findTagNameByTagName(String keyword);
	
	//키워드를 포함한 태그아이디찾기
	@Query(value="select tag_id from tag t where t.tag_name like CONCAT('%',:keyword,'%')", nativeQuery=true)
	List<Integer> findTagIdByTagName(String keyword);
}
