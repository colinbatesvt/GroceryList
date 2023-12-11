package com.github.colinbatesvt.grocerylist.model.GroceryList;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Document("grocerylists")
public class GroceryList {

    @Transient
    public static final String SEQUENCE_NAME = "grocery_list_sequence";

    @Id
    private Long id;
    private String name;
    private String createdBy;
    private LocalDateTime createdOn;
    private List<GroceryListItem> items;

    public GroceryList() {
        this.name = "";
    }

    public GroceryList(String name, String createdBy) {
        super();
        this.id = null;
        this.name = name;
        this.createdBy = createdBy;
        this.createdOn = LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    public void addItem(GroceryListItem item) {
        this.items.add(item);
    }
}