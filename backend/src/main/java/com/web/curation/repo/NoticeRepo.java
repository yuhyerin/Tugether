package com.web.curation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.notice.Notice;

@Repository
public interface NoticeRepo extends JpaRepository<Notice, String>{
	
	@Query(value="select * from notice n where n.notice_to=:email order by notice_id desc", nativeQuery = true)
	public List<Notice> findNotices(String email);
	
	@Query(value="delete from notice where notice_from=:notice_from and article_id=:article_id", nativeQuery=true)
	void deleteNotice(String notice_from, int article_id);

	@Query(value="delete from notice where article_id=:articleid", nativeQuery=true)
	public void deleteNoticeByArticleId(int articleid);

	@Query(value="delete from notice where comment_id=:comment_id", nativeQuery=true)
	void deleteByComment_ID(int comment_id);

}
