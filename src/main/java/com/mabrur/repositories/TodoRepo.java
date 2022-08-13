package com.mabrur.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.mabrur.entities.Todo;

public interface TodoRepo extends JpaRepository<Todo, Long> {

    
    
}
