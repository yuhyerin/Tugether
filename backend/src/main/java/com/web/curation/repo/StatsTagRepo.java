package com.web.curation.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.dto.tag.Tag;


public interface StatsTagRepo extends JpaRepository<Tag, String>{

	@Query(value="select * from tag order by search_cnt desc limit 5", nativeQuery = true)
	ArrayList<Tag> getTopSearchKeyword();

	@Query(value="select * from tag order by article_cnt desc limit 5", nativeQuery = true)
	ArrayList<Tag> getTopArticleTag();
	
	
}
