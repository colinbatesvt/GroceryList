package com.github.colinbatesvt.grocerylist.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("user")
public class User {

    @Id
    private long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
}
