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
import com.mabrur.data.RoleToUserForm;
import com.mabrur.data.UserForm;
import com.mabrur.entities.User;
import com.mabrur.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<ResponseData<List<User>> > getUsers() { 
        ResponseData<List<User>> response = new ResponseData<List<User>>();
        response.setStatus(200);
        response.setMessage("Success");
        response.setPayload(userService.getUsers());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<ResponseData<User>> saveUser(@Valid @RequestBody UserForm userForm) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user").toUriString());
        ResponseData<User> response = new ResponseData<User>();
        response.setStatus(201);
        response.setMessage("Success");
        response.setPayload(userService.saveUser(modelMapper.map(userForm, User.class)));
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseData<User>> updateUser(@Valid @RequestBody UserForm userForm, @PathVariable("id") Long id){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/{id}").toUriString());

        ResponseData<User> response = new ResponseData<>();
        User user = modelMapper.map(userForm, User.class);
        userService.getUser(id).ifPresentOrElse(userUpdated -> {
           
            userService.saveUser(userUpdated, user);

            response.setStatus(203);
            response.setMessage("User successfully updated");
            response.setPayload(userUpdated);
        },  () -> {
            userService.saveUser(user);

            response.setStatus(201);
            response.setMessage("User successfully saved");
            response.setPayload(user);
        });

        return ResponseEntity.created(uri).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseData<Object>> deleteUser(@PathVariable("id") Long id){
        
        ResponseData<Object> response = new ResponseData<>();

        userService.getUser(id).ifPresentOrElse(user -> {
            userService.deleteUserById(id);
            response.setStatus(200);
            response.setMessage("User successfully deleted");
        }, () -> {
            response.setStatus(404);
            response.setMessage("User not found");
        });
        
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addrole")
    public ResponseEntity<?> saveRoleToUser(@Valid @RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
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
