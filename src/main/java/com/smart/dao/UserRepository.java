package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	
	//we call a getUser..()passing email and we will get user which email matches
	@Query("select u from User u where u.email=:email")
	public User getUserByUserName(@Param("email") String email);
}
