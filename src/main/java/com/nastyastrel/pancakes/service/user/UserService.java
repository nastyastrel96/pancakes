package com.nastyastrel.pancakes.service.user;

import com.nastyastrel.pancakes.model.user.User;

import java.security.Principal;

public interface UserService {
    User getAuthenticatedUser(Principal principal);
}
