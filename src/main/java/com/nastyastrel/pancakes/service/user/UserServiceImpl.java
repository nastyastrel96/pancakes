package com.nastyastrel.pancakes.service.user;

import com.nastyastrel.pancakes.model.user.User;
import com.nastyastrel.pancakes.repository.UserRepository;
import com.nastyastrel.pancakes.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public User getAuthenticatedUser(Principal principal) {
        return userRepository.findByLogin(principal.getName()).get();
    }
}
