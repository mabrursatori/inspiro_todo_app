package com.mabrur.services;

import java.util.List;
import java.util.Optional;

import com.mabrur.entities.Todo;


public interface TodoService {
    Todo saveTodo(Todo todo);
    List<Todo> getTodos();
    Todo saveTodo(Todo todo, Todo todoUpdated);
    void deleteTodoById(Long id);
    Optional<Todo> getTodo(Long id);
}
