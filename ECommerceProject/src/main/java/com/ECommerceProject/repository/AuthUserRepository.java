package com.ECommerceProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ECommerceProject.model.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> 
{
	Optional<AuthUser> findByEmail(String email);
	Optional<AuthUser> findByToken(String token);
}
