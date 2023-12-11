package com.github.colinbatesvt.grocerylist.model.GroceryList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateGroceryListRequest {
    public String listName;
    public String userName;

    public CreateGroceryListRequest(String listName, String userName) {
        this.listName = listName;
        this.userName = userName;
    }
}
