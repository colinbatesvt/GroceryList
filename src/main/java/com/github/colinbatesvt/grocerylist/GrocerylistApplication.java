package com.github.colinbatesvt.grocerylist;

import com.github.colinbatesvt.grocerylist.model.GroceryListItem;
import com.github.colinbatesvt.grocerylist.repository.ListItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class GrocerylistApplication {

	@Autowired
	ListItemRepository groceryItemRepo;

	public static void main(String[] args) {
		SpringApplication.run(GrocerylistApplication.class, args);
	}
}
