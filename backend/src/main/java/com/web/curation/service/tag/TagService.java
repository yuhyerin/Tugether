package com.web.curation.service.tag;

import com.web.curation.dto.tag.Tag;

public interface TagService {

	// 태그테이블에서 태그명으로 조회하기 
	int getTagByTagName(String string);
	
	void addTag(String tagname);

}
