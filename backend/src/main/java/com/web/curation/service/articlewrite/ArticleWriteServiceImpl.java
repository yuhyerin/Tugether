package com.web.curation.service.articlewrite;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.web.curation.dto.BasicResponse;
import com.web.curation.dto.article.Article;
import com.web.curation.dto.tag.Tag;
import com.web.curation.repo.ArticleWriteRepo;
import com.web.curation.repo.ArticleWriteTagRepo;
import com.web.curation.repo.ProfileRepo;

@Service
public class ArticleWriteServiceImpl implements ArticleWriteService{

	@Autowired
	ProfileRepo profileRepo;
	
	@Autowired
	ArticleWriteRepo articleWriteRepo;
	
	@Autowired
	ArticleWriteTagRepo articleWriteTagRepo;
	
	// Article 테이블에 insert 하고 article_id 반환. - 이미지등록함 
	@Override
	public int addArticlewithImage(Article article) {
		
		int article_id = 1;
		try {
			String email = article.getEmail();
			String writer = article.getWriter();
			String image = article.getImage();
			String content = article.getContent();
			String link = article.getLink();
			articleWriteRepo.insertArticle(email, writer, image, content, link);
			article_id = articleWriteRepo.findArticleByImage(image);
			
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return article_id;
		
	}
	
	// Article 테이블에 insert 하고 article_id 반환. - 이미지X
	@Override
	public int addArticle(Article article) {
		
		int article_id = 1;
		try {
			String email = article.getEmail();
			String writer = article.getWriter();
			String content = article.getContent();
			String link = article.getLink();
			articleWriteRepo.insertArticlenoImage(email, writer, content, link);
			article_id = articleWriteRepo.findArticleByEmailAndLink(email, link);
			
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return article_id;
		
	}

	// ArticleTag 테이블에 insert & tag테이블에서 article_cnt 증가
	@Override
	public void addArticleTag(int article_id, ArrayList<String> taglist) {
		
		try {
			// 한글로 된 태그들이 tag 테이블에 있는지 검사 
			for(int i=0; i<taglist.size(); i++) {
				Tag tag = articleWriteTagRepo.findTagByTagName(taglist.get(i));
				if(tag==null) { //없으면 우선 Tag테이블에 넣는다. article_cnt 1로 넣는다.
					articleWriteTagRepo.insertTag(taglist.get(i), 1);
					tag = articleWriteTagRepo.findTagByTagName(taglist.get(i));
					
				}else { // 있으면 그 아이의 article_cnt+1하기.
					tag = articleWriteTagRepo.findTagByTagName(taglist.get(i));
					articleWriteTagRepo.countArticleCnt(tag.getTag_id());
				}
				int tag_id = tag.getTag_id();
				// articletag 테이블에 넣기 
				articleWriteRepo.insertArticleTag(article_id, tag_id);
			}
			
			
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
	
}
