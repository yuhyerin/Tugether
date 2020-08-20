package com.web.curation.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.article.Following;

@Repository
public interface FollowingRepo extends JpaRepository<Following, String> {
	
	@Query(value="select to_user from following f where f.from_user=:email order by uid desc", nativeQuery = true)
	public List<String> findToUserByFromUser(String email);
	
	@Query(value="select from_user from following f where f.to_user=:email order by uid desc" , nativeQuery = true)
	public List<String> findFromUserByToUser(String email);
	
	@Query(value="select * from following f where f.from_user=:from_user and f.to_user=:to_user", nativeQuery = true)
	public Optional<Following> findOptionByFromAndTo(String from_user, String to_user);

	@Query(value="insert into following(from_user, to_user) values( :from_email , :to_email )", nativeQuery = true)
	public void addFollow(String from_email, String to_email);
	
	@Query(value="delete from following where from_user = :from_email and to_user = :to_email ", nativeQuery = true)
	public void deleteFollow(String from_email, String to_email);
	
}
