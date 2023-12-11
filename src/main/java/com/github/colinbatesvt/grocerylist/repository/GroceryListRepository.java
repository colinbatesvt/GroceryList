package com.github.colinbatesvt.grocerylist.repository;

import com.github.colinbatesvt.grocerylist.model.GroceryList.GroceryList;
import com.github.colinbatesvt.grocerylist.model.GroceryList.GroceryListItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroceryListRepository extends MongoRepository<GroceryList, String> {

    @Query("{createdBy:'?0'}")
    List<GroceryList> findListsByUserName(String userName);

    @Query("{_id:?0}")
    Optional<GroceryList> findListById(Long id);

    public long count();

}
