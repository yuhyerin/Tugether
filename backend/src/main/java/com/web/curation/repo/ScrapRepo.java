package com.web.curation.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.web.curation.dto.article.Scrap;

public interface ScrapRepo extends JpaRepository<Scrap, String> {
	
	@Query(value="select * from scrap s where s.article_id=:article_id and s.email=:email", nativeQuery = true)
	public Optional<Scrap> findScrap(int article_id, String email);

}
