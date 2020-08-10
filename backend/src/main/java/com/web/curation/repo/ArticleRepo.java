package com.web.curation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.article.Article;

@Repository
public interface ArticleRepo extends JpaRepository<Article, String>{

	@Query(value="select * from article a where a.article_id=:article_id order by article_id desc", nativeQuery = true)
	public List<Article> findArticleByArticleId(int article_id);

	@Query(value="select * from article a where a.article_id=:article_id and a.email!=:email order by article_id desc", nativeQuery = true)
	public List<Article> findArticleByArticleIdandEmail(int article_id, String email);
	
	@Query(value="select * from article a where a.email=:email order by article_id desc", nativeQuery = true)
	public List<Article>findArticleByEmail(String email);
	
	@Query(value="select image from article a where a.article_id=:article_id", nativeQuery = true)
	String findImgByArticleId(int article_id);
	
}
