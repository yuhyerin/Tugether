package com.web.curation.service.notice;

import java.util.List;

import com.web.curation.dto.notice.FrontNotice;
import com.web.curation.dto.notice.Notice;

public interface NoticeService {
	
	List<FrontNotice> findNotice(String email);
	FrontNotice makeFront(Notice notice, String email);
	void deleteNoticeByArticleId(int articleid);

}
