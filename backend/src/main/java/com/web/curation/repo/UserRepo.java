
package com.web.curation.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.curation.dto.account.User;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    

	User findEmailByEmail(String email);
	String findPasswordByEmail(String email);
	User findUserByEmail(String email);
	User findUserByEmailAndPassword(String email, String password);

}
