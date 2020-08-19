package com.web.curation.service.scrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.repo.ScrapRepo;

@Service
public class ScrapServiceImpl implements ScrapService{

	@Autowired
	private ScrapRepo scrapRepo;
	
	@Override
	public void deleteScrapByArticleId(int articleid) {
		scrapRepo.deleteScrapByArticleId(articleid);
	}

	@Override
	public void deleteScrapByArticleIdAndEmail(String email, int article_id) {
		scrapRepo.deleteScrapByArticleIdAndEmail(email, article_id);
	}



}
