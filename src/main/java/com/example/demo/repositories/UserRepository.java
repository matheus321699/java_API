package com.example.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.domain.user.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {
	
	UserDetails findByLogin(String login);
	
}
