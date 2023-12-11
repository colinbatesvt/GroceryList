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
    private List<GroceryListItem> items;

    public GroceryListDto() {
        this.id = 0L;
        this.name = "";
        this.createdBy = "";
        this.createdOn = LocalDateTime.MIN;
        this.items = new ArrayList<>();
    }

    public GroceryListDto(Long id, String name, String createdBy, LocalDateTime createdOn, List<GroceryListItem> items) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.items = items;
    }

}
