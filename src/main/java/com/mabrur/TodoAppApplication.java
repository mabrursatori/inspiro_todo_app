package com.mabrur;

import java.util.ArrayList;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mabrur.data.TodoForm;
import com.mabrur.entities.Role;
import com.mabrur.entities.Todo;
import com.mabrur.entities.User;
import com.mabrur.services.TodoService;
import com.mabrur.services.UserService;

@SpringBootApplication
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addConverter(new AbstractConverter<TodoForm,Todo>() {
			@Override
			protected Todo convert(TodoForm source) {
				return new Todo(source.getId(), 
				source.getTitle(), 
				source.getDescription(), 
				(source.getChecked() == null) ? false : source.getChecked());
			}	
		});
		return modelMapper;
	}

	@Bean
	CommandLineRunner run(UserService userService, TodoService todoService) {
		return args -> {
			userService.saveRole(new Role(null, "USER"));
			userService.saveRole(new Role(null, "ADMIN"));

			userService.saveUser(new User(null,"john", "1234", new ArrayList<>()));
			userService.saveUser(new User(null,"will", "1234", new ArrayList<>()));

			userService.addRoleToUser("john", "USER");
			userService.addRoleToUser("will", "ADMIN");

			Todo newTodo = new Todo();
			newTodo.setTitle("Learn Spring Boot");
			newTodo.setDescription("Learn Spring Boot by the documentation by myself");
			todoService.saveTodo(newTodo);

			Todo newTodo2 = new Todo();
			newTodo2.setTitle("Learn React");
			newTodo2.setDescription("Learn React by the documentation by myself");
			newTodo2.setChecked(true);
			todoService.saveTodo(newTodo2);
		};
	}

}
