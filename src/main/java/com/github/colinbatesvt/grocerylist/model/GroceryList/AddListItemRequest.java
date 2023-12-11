package com.github.colinbatesvt.grocerylist.model.GroceryList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddListItemRequest {
    public Long listId;
    public GroceryListItem item;

    public AddListItemRequest(Long listId, GroceryListItem item) {
        this.listId = listId;
        this.item = item;
    }
}
