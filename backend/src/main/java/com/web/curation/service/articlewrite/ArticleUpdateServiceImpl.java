package com.web.curation.service.articlewrite;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.curation.dto.article.Article;
import com.web.curation.dto.tag.Tag;
import com.web.curation.repo.ArticleUpdateRepo;
import com.web.curation.repo.ArticleWriteRepo;
import com.web.curation.repo.ArticleWriteTagRepo;
import com.web.curation.repo.ProfileRepo;
import com.web.curation.repo.TagRepo;

@Service
public class ArticleUpdateServiceImpl implements ArticleUpdateService {

	@Autowired
	ArticleUpdateRepo articleUpdateRepo;
	@Autowired
	ArticleWriteRepo articleWriteRepo;
	@Autowired
	ArticleWriteTagRepo articleWriteTagRepo;
	@Autowired
	ProfileRepo profileRepo;
	@Autowired
	ArticleWriteRepo articleRepo;

	@Autowired
	TagRepo tagRepo;

	// 게시글번호 보내서 내용 가져오기
	@Override
	public Article getArticle(int article_id) {
		try {

			return articleWriteRepo.getArticle(article_id);

		} catch (NullPointerException e) {

			System.out.println("해당 article_id의  글은 없습니다. 빈 아티클을 반환합니다. ");
			Article tmp = new Article();
			return tmp;
		}
	}

	/**
	 * 1. articletag에서 지우기 
	 * 2. tag테이블에서 article_cnt -1 하기  */
	@Override
	public void resetArticleTag(int article_id, ArrayList<String> taglist) {
		// 1. articletag에서 지우기 
		articleUpdateRepo.resetArticleTag(article_id);
		// 2. tag테이블에서 article_cnt -1 하기
		for(int i=0; i<taglist.size(); i++) {
			Tag tag = articleWriteTagRepo.findTagByTagName(taglist.get(i));
			articleWriteTagRepo.countArticleMinusCnt(tag.getTag_id());
		}
		
	}

	// 게시글에 등록한 태그리스트 가져오기
	@Override
	public ArrayList<String> getArticleTag(int article_id) {

		ArrayList<Integer> tagIdList = articleUpdateRepo.getArticleTags(article_id);
		ArrayList<String> tagNameList = new ArrayList<String>();
		for (int i = 0; i < tagIdList.size(); i++) {
			tagNameList.add(tagRepo.findTagNameByTagId(tagIdList.get(i)));
		}
		return tagNameList;
	}

	// 게시글 수정 - 이미지 변경했을 때
	@Override
	public void updateArticlewithImage(Article article) {

		int article_id = article.getArticle_id();
		String content = article.getContent();
		String link = article.getLink();
		String image = article.getImage();
		articleWriteRepo.updateArticlewithImage(article_id, image, content, link);

	}

	// 게시글 수정
	@Override
	public void updateArticle(Article article) {

		int article_id = article.getArticle_id();
		String content = article.getContent();
		String link = article.getLink();
		articleWriteRepo.updateArticle(article_id, content, link);

	}


	// article 테이블에서 삭제
	@Override
	public void deleteArticle(String email, int article_id) {
		
		articleWriteRepo.deleteArticle(article_id);
		
	}

	// article에서 writer 변경하기
	@Override
	public void updateArticleWriter(String email, String nickname) {

		articleWriteRepo.updateArticleWriter(email, nickname);
	}

}
