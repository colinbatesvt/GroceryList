package com.github.colinbatesvt.grocerylist.model.GroceryList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CreateGroceryListRequest {
    public String listName;
}
