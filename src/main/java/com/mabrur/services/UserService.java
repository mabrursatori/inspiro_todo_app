package com.mabrur.services;

import java.util.List;
import java.util.Optional;

import com.mabrur.entities.Role;
import com.mabrur.entities.User;

public interface UserService {
    User saveUser(User user);
    User saveUser(User user, User updatedUser);
    User getUser(String username);
    Optional<User> getUser(Long id);
    List<User> getUsers();
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    void deleteUserById(Long id);
}
