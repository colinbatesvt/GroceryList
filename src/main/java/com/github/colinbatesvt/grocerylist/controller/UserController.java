package com.github.colinbatesvt.grocerylist.controller;

import com.github.colinbatesvt.grocerylist.model.auth.Role;
import com.github.colinbatesvt.grocerylist.model.auth.User;
import com.github.colinbatesvt.grocerylist.model.auth.LoginDto;
import com.github.colinbatesvt.grocerylist.model.auth.SignUpDto;
import com.github.colinbatesvt.grocerylist.repository.UserRepository;
import com.github.colinbatesvt.grocerylist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<String> getUser(@PathVariable Long id){
        return new ResponseEntity<String>(userService.getById(id).getUsername(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        if(userRepository.findByUsername(signUpDto.getUsername()).isPresent()){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setRole(Role.ROLE_USER.toString());
        user.setPassword(signUpDto.getPassword());

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @GetMapping("")
    public User getCurrentUser() {
        User userInfo = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null && null != securityContext.getAuthentication()) {
            String principal =  (String)securityContext.getAuthentication().getPrincipal();

            userInfo = userRepository.findByUsername(principal).orElseThrow(() ->
                    new UsernameNotFoundException("You are not currently logged in"));
            //TODO: should have a DTO with no pword field. Don't want to send this back
            userInfo.setPassword("");
        }
        return userInfo;
    }
}