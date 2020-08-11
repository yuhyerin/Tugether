package com.web.curation.service.articlewrite;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.repo.ArticleUpdateRepo;
import com.web.curation.repo.ArticleWriteRepo;
import com.web.curation.repo.TagRepo;

@Service
public class ArticleUpdateServiceImpl implements ArticleUpdateService {

	@Autowired
	ArticleUpdateRepo articleUpdateRepo;
	
	@Autowired
	ArticleWriteRepo articleWriteRepo;
	
	@Autowired
	TagRepo tagRepo;
	
	//게시글번호 보내서 내용 가져오기
	@Override
	public Article getArticle(int article_id) {
		try {
			
			return articleWriteRepo.getArticle(article_id);
			
		}catch(NullPointerException e) {
			
			System.out.println("해당 article_id의  글은 없습니다. 빈 아티클을 반환합니다. ");
			Article tmp = new Article();
			return tmp;
		}
	}

	// 게시글에 등록한 태그들 초기화(다 지우기)
	@Override
	public void resetArticleTag(int article_id) {
		articleUpdateRepo.resetArticleTag(article_id);
	}

	// 게시글에 등록한 태그리스트 가져오기
	@Override
	public ArrayList<String> getArticleTag(int article_id) {
		
		ArrayList<Integer> tagIdList = articleUpdateRepo.getArticleTags(article_id);
		ArrayList<String> tagNameList = new ArrayList<String>();
		for(int i=0; i<tagIdList.size(); i++) {
			tagNameList.add(tagRepo.findTagNameByTagId(tagIdList.get(i)));
		}
		return tagNameList;
	}
	
}
