package com.github.colinbatesvt.grocerylist.service;

import com.github.colinbatesvt.grocerylist.Exception.EntityNotFoundException;
import com.github.colinbatesvt.grocerylist.model.auth.User;
import com.github.colinbatesvt.grocerylist.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public User getByUsername(String username) {
        return unwrapUser(userRepository.findByUsername(username), 404L);
    }

    public User getById(Long id) {
        return unwrapUser(userRepository.findById(id), id);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, User.class);
    }
}
