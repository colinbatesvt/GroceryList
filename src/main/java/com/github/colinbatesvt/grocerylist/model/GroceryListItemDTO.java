package com.github.colinbatesvt.grocerylist.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroceryListItemDTO {

    private String name;
    private int quantity;
    private String category;

    public GroceryListItemDTO() {
        this.name = "";
        this.category = "";
        this.quantity = 0;
    }

    public GroceryListItemDTO(String name, int quantity, String category) {
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

}
