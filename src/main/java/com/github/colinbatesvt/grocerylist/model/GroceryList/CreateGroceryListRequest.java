package com.github.colinbatesvt.grocerylist.model.GroceryList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class CreateGroceryListRequest {
    public String listName;
    public String userName;
}
