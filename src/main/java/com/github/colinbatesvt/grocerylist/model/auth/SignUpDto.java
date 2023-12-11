package com.github.colinbatesvt.grocerylist.model.auth;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
