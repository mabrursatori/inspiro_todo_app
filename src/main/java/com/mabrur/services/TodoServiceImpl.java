package com.mabrur.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mabrur.entities.Todo;
import com.mabrur.repositories.TodoRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepo todoRepo;

    @Override
    public Todo saveTodo(Todo todo) {
        return todoRepo.save(todo);
    }

    @Override
    public List<Todo> getTodos() {
        return todoRepo.findAll();
    }

    @Override
    public Todo saveTodo(Todo todo, Todo todoUpdated) {
        if(todoUpdated.getTitle() != null) todo.setTitle(todoUpdated.getTitle());
        if(todoUpdated.getDescription() != null) todo.setDescription(todoUpdated.getDescription());
        if(todoUpdated.getChecked() != null) todo.setChecked(todoUpdated.getChecked());
        return todoRepo.save(todo);
    }

    @Override
    public void deleteTodoById(Long id) {
        todoRepo.deleteById(id);
        
    }

    @Override
    public Optional<Todo> getTodo(Long id) {
        return todoRepo.findById(id);
    }
    
}
