package com.web.curation.service.scrap;

public interface ScrapService {

	// article_id로 스크랩테이블에서 삭제
	void deleteScrapByArticleId(int articleid);
	// scrap_id로 스크랩테이블에서 삭제
	void deleteScrapByArticleIdAndEmail(String email, int article_id);

}
