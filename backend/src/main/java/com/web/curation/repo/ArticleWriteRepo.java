package com.web.curation.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.article.Article;

@Repository
@Transactional
public interface ArticleWriteRepo extends JpaRepository<Article, String>{

	@Query(value="insert into article(email,writer,image,content,link) values(:email , :writer, :image, :content, :link )", nativeQuery=true)
	void insertArticle(String email, String writer, String image, String content, String link);
	
	@Query(value="select article_id from article where image=:image order by reg_time desc limit 1", nativeQuery=true)
	int findArticleByImage(String image);
	
	@Query(value="insert into articletag(article_id, tag_id) values(:article_id , :tag_id )", nativeQuery=true)
	void insertArticleTag(int article_id, int tag_id);
	
	
}
