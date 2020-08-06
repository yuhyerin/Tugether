package com.web.curation.service.tag;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;



public interface FavtagService {
	
	// favtag 테이블에 insert 하는 문장 
	public ResponseEntity<Object> addFavtag(String uid, List<Integer> favtaglist );
	
	// 해당사용자가 관심태그 설정 했었는지 검사.
	public Boolean isSelectFavtag(String uid);
	
	// 사용자의 관심태그 리스트 조회 
	public ArrayList<String> getFavtagList(String email);
	
}
