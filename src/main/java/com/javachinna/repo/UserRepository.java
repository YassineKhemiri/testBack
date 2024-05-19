package com.javachinna.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.javachinna.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);

	User findByEmailAndCin (String email, Long cin);


	User findByNum(String num);



	boolean existsByNum(String num);
	boolean existsByEmail(String email);


}
