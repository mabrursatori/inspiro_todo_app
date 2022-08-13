package com.mabrur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mabrur.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByName(String name);

}
