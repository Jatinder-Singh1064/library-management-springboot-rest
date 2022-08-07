package com.library.management.service.impl;

import com.library.management.model.User;
import com.library.management.repository.UserRepository;
import com.library.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void saveUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        userRepository.save(user);
    }

    @Override
    public User findUserById(long userId) {
        User user = userRepository.findById(userId).get();
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<User> searchUser(String firstName, String lastName) {
        if (firstName != null && lastName != null) return getUserByFullName(firstName, lastName);
        else if (firstName == null && lastName != null) return getUserByLastName(lastName);
        else if (firstName != null && lastName == null) return getUserByFirstName(firstName);
        else return new ArrayList<User>();
    }

    private List<User> getUserByFirstName(String firstName) {
        List<User> users = new ArrayList<User>();
        for (User user : userRepository.findAll()) {
            if (user.getFirstName().toLowerCase().contains(firstName.toLowerCase())) {
                users.add(user);
            }
        }
        return users;
    }

    private List<User> getUserByLastName(String lastName) {
        List<User> users = new ArrayList<User>();
        for (User user : userRepository.findAll()) {
            if(user.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                users.add(user);
            }
        }
        return users;
    }

    private List<User> getUserByFullName(String firstName, String lastName) {
        List<User> users = new ArrayList<User>();
        for (User user : userRepository.findAll()) {
            if (user.getFirstName().toLowerCase().contains(firstName.toLowerCase()) &&
                    user.getLastName().toLowerCase().contains(lastName.toLowerCase())) {
                users.add(user);
            }
        }
        return users;
    }
}
