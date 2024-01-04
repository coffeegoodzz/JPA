package com.example.study.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.study.Entity.SiteUser;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long> {
	
	Optional<SiteUser> findByusername(String username);
}
