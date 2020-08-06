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
	ArticleWriteRepo articleRepo;
	
	@Autowired
	ArticleWriteTagRepo articleTagRepo;
	
	@Override
	public int addArticle(Article article) {
		
		int article_id = 1;
		try {
			String email = article.getEmail();
			String writer = article.getWriter();
			String image = article.getImage();
			String content = article.getContent();
			String link = article.getLink();
			articleRepo.insertArticle(email, writer, image, content, link);
			System.out.println("디비에 글을 넣었습니다.");
			profileRepo.countMyArticle(email); // 내 게시글 갯수 하나 추가
			System.out.println("내 게시글 갯수 하나 추가했습니다.");
			
			article_id = articleRepo.findArticleByImage(image);
			
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		return article_id;
		
	}

	@Override
	public void addArticleTag(int article_id, ArrayList<String> taglist) {
		
		ResponseEntity<Object> response = null;
		BasicResponse result = new BasicResponse();
		
		
		try {
			
			// 한글로 된 태그들이 tag 테이블에 있는지 검사 
			for(int i=0; i<taglist.size(); i++) {
				Tag tag = articleTagRepo.findTagByTagName(taglist.get(i));
				if(tag==null) { //없으면 
					articleTagRepo.insertTag(taglist.get(i));
					tag = articleTagRepo.findTagByTagName(taglist.get(i));
					
				}
				int tag_id = tag.getTag_id();
				// articletag 테이블에 넣기 
				articleRepo.insertArticleTag(article_id, tag_id);
				System.out.println("ArticleTag에 드디어!!! insert 했습니다. ");
			}
			
			
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}

	
}
