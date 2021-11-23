package com.Subji.webservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Subji.webservice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);

}
