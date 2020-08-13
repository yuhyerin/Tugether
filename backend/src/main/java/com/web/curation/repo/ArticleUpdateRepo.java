package com.web.curation.repo;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.article.ArticleTag;

@Repository
public interface ArticleUpdateRepo extends JpaRepository<ArticleTag, String>{
	
	@Query(value="delete from articletag where article_id= :article_id",nativeQuery=true)
	void resetArticleTag(int article_id);
	
	@Query(value="select tag_id from articletag where article_id = :article_id", nativeQuery = true)
	ArrayList<Integer> getArticleTags(int article_id);
	
}
