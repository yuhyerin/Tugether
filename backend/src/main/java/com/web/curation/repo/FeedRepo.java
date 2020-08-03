package com.web.curation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.account.User;
import com.web.curation.dto.article.Article;

@Repository
public interface FeedRepo extends JpaRepository<User, String> {

	@Query(value="select tag_id from favtag f where f.email=:email", nativeQuery = true)
	public List<String> findTagIdByEmail(String email);
	
	@Query(value="select article_id from articletag a where a.tag_id=:tag_id", nativeQuery = true)
	public List<String> findArticleIdByTagId(String tag_id);
	
	@Query(value="select * from article a where a.article_id=:article_id", nativeQuery = true)
	public Article findArticleByArticleId(String article_id);
	
	@Query(value="select to_user from following f where f.from_user=:email", nativeQuery = true)
	public List<String> findToUserByFromUser(String email);
}
