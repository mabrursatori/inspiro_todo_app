package com.mabrur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mabrur.entities.User;

public interface UserRepo  extends JpaRepository<User, Long> {

    User findByUsername(String username);

    
    
}
