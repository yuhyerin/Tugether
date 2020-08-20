package com.web.curation.service.stats;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.tag.Tag;
import com.web.curation.repo.StatsArticleRepo;
import com.web.curation.repo.StatsTagRepo;
import com.web.curation.service.mypage.MyPageService;


@Service
public class StatsServiceImpl implements StatsService{

	@Autowired
	private StatsTagRepo statsTagRepo;
	@Autowired
	private StatsArticleRepo statsArticleRepo;
	
	@Override
	public ArrayList<Tag> getTopSearchKeyword() {
		ArrayList<Tag> taglist = statsTagRepo.getTopSearchKeyword();
		return taglist;
	}

	@Override
	public ArrayList<Tag> getTopArticleTag() {
		ArrayList<Tag> taglist = statsTagRepo.getTopArticleTag();
		return taglist;
	}

	@Override
	public List<Article> getTopLikeyArticle(String email) {
		return statsArticleRepo.getTopLikeyArticle();
	}
	
	

	
	
}
