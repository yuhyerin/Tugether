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
	
	@Query(value="insert into article(email,writer,content,link) values(:email , :writer, :content, :link )", nativeQuery=true)
	void insertArticlenoImage(String email, String writer, String content, String link);
	
	@Query(value="select article_id from article where image=:image order by reg_time desc limit 1", nativeQuery=true)
	int findArticleByImage(String image);
	
	@Query(value="select article_id from article where email=:email and link= :link order by reg_time desc limit 1", nativeQuery=true)
	int findArticleByEmailAndLink(String email, String link);
	
	@Query(value="insert into articletag(article_id, tag_id) values(:article_id , :tag_id )", nativeQuery=true)
	void insertArticleTag(int article_id, int tag_id);
	
	@Query(value="select count(*) from article a where a.email=:email ", nativeQuery = true)
	public int countMyArticle(String email);
	
	@Query(value="select * from article where article_id=:article_id", nativeQuery=true)
	Article getArticle(int article_id);
	
	@Query(value="update article a set a.content= :content, a.link = :link where a.article_id = :article_id",nativeQuery = true)
	void updateArticle(int article_id, String content,String link);
	
	@Query(value="update article a set a.image = :image , a.content= :content, a.link = :link where a.article_id = :article_id",nativeQuery = true)
	void updateArticlewithImage(int article_id,String image,String content,String link);

	@Query(value="delete from article where article_id= :article_id",nativeQuery=true)
	void deleteArticle(int article_id);

	@Query(value="update article a set a.writer = :nickname where a.email = :email",nativeQuery = true)
	void updateArticleWriter(String email, String nickname);


}

