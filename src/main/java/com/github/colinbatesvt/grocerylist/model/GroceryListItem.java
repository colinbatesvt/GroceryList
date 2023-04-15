package com.github.colinbatesvt.grocerylist.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter
@Document("grocerylistitems")
public class GroceryListItem {

    @Id
    private String id;
    private String name;
    private int quantity;
    private String category;

    public GroceryListItem() {
        this.name = "";
        this.quantity = 0;
        this.category = "";
    }

    public GroceryListItem(String id, String name, int quantity, String category) {
        super();
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }


}