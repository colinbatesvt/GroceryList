package com.github.colinbatesvt.grocerylist.model.auth;

import lombok.Data;

@Data
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}