package com.web.curation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.article.ArticleTag;

@Repository
public interface ArticleTagRepo extends JpaRepository<ArticleTag, String>{
	
	@Query(value="select article_id from articletag a where a.tag_id=:tag_id", nativeQuery = true)
	public List<Integer> findArticleIdByTagId(int tag_id);
	
	@Query(value="select tag_id from articletag a where a.article_id=:article_id", nativeQuery = true)
	public List<Integer> findTagIdByArticleId(int article_id);

}
