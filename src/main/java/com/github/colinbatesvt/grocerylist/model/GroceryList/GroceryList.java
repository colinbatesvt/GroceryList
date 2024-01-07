package com.github.colinbatesvt.grocerylist.model.GroceryList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @NoArgsConstructor
@Document("grocerylists")
public class GroceryList {

    @Transient
    public static final String SEQUENCE_NAME = "grocery_list_sequence";

    @Id
    private Long id;
    private String name;
    private String createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    private List<GroceryListItem> items;

    public GroceryList(String name, String createdBy) {
        super();
        this.id = null;
        this.name = name;
        this.createdBy = createdBy;
        this.createdOn = LocalDateTime.now();
        this.lastUpdatedOn = LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    public void addItem (GroceryListItem item) {
        this.items.add(item);
    }

    public void removeItem(int index) {
        this.items.remove(index);
    }

    public void updateItem(int index, GroceryListItem item) {
        this.items.set(index, item);
    }

    public void removeCheckedItems() {
        this.items = this.items.stream().filter(item -> !item.isChecked()).collect(Collectors.toList());
    }

    public void removeAllItems() {
        this.items = new ArrayList<>();
    }
}