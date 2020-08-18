package com.web.curation.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.dto.article.Likey;

public interface LikeyRepo extends JpaRepository<Likey, String> {
	
	@Query(value="select * from likey l where l.article_id=:article_id and l.email=:email", nativeQuery = true)
	public Optional<Likey> findLike(int article_id, String email);
	
	@Query(value="select * from likey l where l.article_id=:article_id", nativeQuery = true)
	public List<Likey> findLikeByArticleId(int article_id);
	
	@Query(value="delete from likey where email=:email and article_id=:article_id", nativeQuery = true)
	public void deleteLikey(String email, int article_id);
	
	@Query(value="delete from likey where article_id=:articleid", nativeQuery = true)
	public void deleteLikeyByArticleId(int articleid);
}
