package com.github.colinbatesvt.grocerylist.model.GroceryList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter @Setter @AllArgsConstructor
public class UpdateGroceryListRequest {
    private String name;
    private List<GroceryListItem> items;
}
