package com.web.curation.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.article.Following;

@Repository
public interface FollowingRepo extends JpaRepository<Following, String> {
	
	@Query(value="select to_user from following f where f.from_user=:email", nativeQuery = true)
	public List<String> findToUserByFromUser(String email);

}
