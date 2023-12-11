package com.github.colinbatesvt.grocerylist.controller;

import com.github.colinbatesvt.grocerylist.model.auth.Role;
import com.github.colinbatesvt.grocerylist.model.auth.User;
import com.github.colinbatesvt.grocerylist.model.auth.LoginDto;
import com.github.colinbatesvt.grocerylist.model.auth.SignUpDto;
import com.github.colinbatesvt.grocerylist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.findByUsername(signUpDto.getUsername()).isPresent()){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.findByEmail(signUpDto.getEmail()).isPresent()){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setRole(Role.ROLE_USER.toString());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }

    @GetMapping("/user")
    public User getCurrentUser() {
        User userInfo = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (securityContext != null && null != securityContext.getAuthentication()) {
            Object principal = securityContext.getAuthentication().getPrincipal();
            if (User.class.isAssignableFrom(principal.getClass())) {
                userInfo = (User) principal;
            }
        }
        return userInfo;
    }
}