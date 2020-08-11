package com.web.curation.service.mypage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.repo.ArticleRepo;
import com.web.curation.repo.FollowingRepo;
import com.web.curation.repo.ScrapRepo;

@Service
public class MyPageServiceImpl implements MyPageService {
	
	@Autowired
	private ArticleRepo articleRepo;
	@Autowired
	private ScrapRepo scrapRepo;
	@Autowired
	private FollowingRepo followingRepo;
	
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

	@Override
	public boolean findFollow(String userEmail, String email) {
		List<String> list = followingRepo.findToUserByFromUser(email);
		return list.contains(userEmail);
	}
	
	

}
