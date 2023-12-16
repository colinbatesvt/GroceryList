package com.github.colinbatesvt.grocerylist.repository;

import com.github.colinbatesvt.grocerylist.model.auth.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    @Query("{email:'?0'}")
    Optional<User> findByEmail(String email);
    @Query("{username:'?0'}")
    Optional<User> findByUsername(String username);
}
