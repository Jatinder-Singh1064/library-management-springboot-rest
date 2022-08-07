package com.library.management.service;

import com.library.management.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    void saveUserById(Long userId);

    User findUserById(long userId);

    List<User> findAllUsers();

    List<User> searchUser(String firstName, String lastName);
}
