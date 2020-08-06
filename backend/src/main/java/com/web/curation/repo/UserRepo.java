
package com.web.curation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.account.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    
	@Query(value="select email from user u where u.email=:email", nativeQuery = true)
	String findEmailByEmail(String email);
	
//	@Query(value="select password from user u where u.email=:email", nativeQuery = true)
//	String findPasswordByEmail(String email);

	User findUserByEmail(String email);
	
	User findUserByEmailAndPassword(String email, String password);

}
