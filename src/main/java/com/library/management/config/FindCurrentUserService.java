package com.library.management.config;

import com.library.management.model.User;
import com.library.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class FindCurrentUserService {

    @Autowired
    UserService userService;

    public long getCurrentUserId() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = details.getUsername();
        long userId = -1;

        for (User user : userService.findAllUsers()) {
            if (user.getUserName().equals(username)) {
                userId = user.getUserId();
                break;
            }
        }
        return userId;
    }

    public User getCurrentUser() {
        User currentUser = userService.findUserById(getCurrentUserId());
        return currentUser;
    }
}
