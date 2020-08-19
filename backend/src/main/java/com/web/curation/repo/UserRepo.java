
package com.web.curation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.account.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    
	@Query(value="select email from user u where u.email=:email", nativeQuery = true)
	String findEmailByEmail(String email);

	User findUserByEmail(String email);
	
	User findUserByEmailAndPassword(String email, String password);
	
	@Query(value="update user set nickname = :nickname where email= :email ",nativeQuery=true)
	void updateUserNickname(String email, String nickname);

	@Query(value="select * from user u where u.email= :email and u.password is null",nativeQuery=true)
	User findGoogleUserByPassword(String email);

}
