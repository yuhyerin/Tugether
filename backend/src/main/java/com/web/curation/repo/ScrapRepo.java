package com.web.curation.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.dto.article.Scrap;

public interface ScrapRepo extends JpaRepository<Scrap, String> {
	
	@Query(value="select * from scrap s where s.email=:email and s.article_id=:article_id", nativeQuery = true)
	public Optional<Scrap> findScrap(String email, int article_id);
	
	@Query(value="select article_id from scrap s where s.email=:email order by uid desc", nativeQuery = true)
	public List<Integer> findArticleidByEmail(String email);

	@Query(value="delete from scrap where article_id=:articleid", nativeQuery = true)
	public void deleteScrapByArticleId(int articleid);

	@Query(value="delete from scrap where email = :email and article_id = :article_id", nativeQuery = true)
	public void deleteScrapByArticleIdAndEmail(String email, int article_id);

}
