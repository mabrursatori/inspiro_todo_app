package com.mabrur.controllers.API;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.validation.FieldError;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mabrur.data.ResponseData;
import com.mabrur.data.TodoForm;
import com.mabrur.entities.Todo;
import com.mabrur.services.TodoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {
    

    private final TodoService todoService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ResponseData<List<Todo>> > getTodos() { 
        ResponseData<List<Todo>> response = new ResponseData<List<Todo>>();
        response.setStatus(200);
        response.setMessage("Success");
        response.setPayload(todoService.getTodos());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ResponseData<Todo>> saveTodo(@Valid @RequestBody TodoForm todoForm) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/todo").toUriString());
        ResponseData<Todo> response = new ResponseData<Todo>();
        response.setStatus(201);
        response.setMessage("Success");
        response.setPayload(todoService.saveTodo(modelMapper.map(todoForm, Todo.class)));
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Todo>> updateTodo(@Valid @RequestBody TodoForm todoForm, @PathVariable("id") Long id){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/todo/{id}").toUriString());

        ResponseData<Todo> response = new ResponseData<>();

        Todo todo = modelMapper.map(todoForm, Todo.class);

        todoService.getTodo(id).ifPresentOrElse(todoUpdated -> {
           
            todoService.saveTodo(todoUpdated, todo);

            response.setStatus(203);
            response.setMessage("Todo successfully updated");
            response.setPayload(todoUpdated);
        },  () -> {
            todoService.saveTodo(todo);

            response.setStatus(201);
            response.setMessage("Todo successfully saved");
            response.setPayload(todo);
        });

        return ResponseEntity.created(uri).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Object>> deleteTodo(@PathVariable("id") Long id){
        
        ResponseData<Object> response = new ResponseData<>();

        todoService.getTodo(id).ifPresentOrElse(todo -> {
            todoService.deleteTodoById(id);
            response.setStatus(200);
            response.setMessage("Todo successfully deleted");
        }, () -> {
            response.setStatus(404);
            response.setMessage("Todo not found");
        });
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
        MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
    });
    return errors;
    }

}
