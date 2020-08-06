package com.web.curation.service.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.ArticleTagRepo;
import com.web.curation.repo.CommentRepo;
import com.web.curation.repo.LikeyRepo;
import com.web.curation.repo.ScrapRepo;
import com.web.curation.repo.TagRepo;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private ScrapRepo scrapRepo;
	
	@Override
	public List<Article> findArticles(String email) {
		return articleRepo.findArticleByEmail(email);
	}

	@Override
	public List<Article> findScraps(String email) {
		List<Integer> articleIDs = scrapRepo.findArticleidByEmail(email);
		List<Article> articles = new ArrayList<Article>();
		for(int l=0;l<articleIDs.size();l++)
			articles.add(articleRepo.findArticleByArticleId(articleIDs.get(l)).get(0)); 
		return articles;
	}
	
	

}
