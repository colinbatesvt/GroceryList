package com.github.colinbatesvt.grocerylist.repository;

import com.github.colinbatesvt.grocerylist.model.GroceryListItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ListItemRepository extends MongoRepository<GroceryListItem, String> {

    @Query("{name:'?0'}")
    GroceryListItem findItemByName(String name);

    @Query(value="{category:'?0'}", fields="{'name' : 1, 'quantity' : 1}")
    List<GroceryListItem> findAll(String category);

    public long count();

}
