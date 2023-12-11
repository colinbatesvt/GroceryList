package com.github.colinbatesvt.grocerylist.model.GroceryList;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GroceryListDto {

    private Long id;
    private String name;
    private String createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    private List<GroceryListItem> items;

    public GroceryListDto() {
        this.id = 0L;
        this.name = "";
        this.createdBy = "";
        this.createdOn = LocalDateTime.MIN;
        this.lastUpdatedOn = LocalDateTime.MIN;
        this.items = new ArrayList<>();
    }

    public GroceryListDto(GroceryList list) {
        this.id = list.getId();
        this.name = list.getName();
        this.createdBy = list.getCreatedBy();
        this.createdOn = list.getCreatedOn();
        this.lastUpdatedOn = list.getLastUpdatedOn();
        this.items = list.getItems();
    }

}
