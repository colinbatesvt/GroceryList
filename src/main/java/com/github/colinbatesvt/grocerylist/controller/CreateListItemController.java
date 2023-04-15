package com.github.colinbatesvt.grocerylist.controller;

import com.github.colinbatesvt.grocerylist.model.GroceryListItem;
import com.github.colinbatesvt.grocerylist.model.GroceryListItemDTO;
import com.github.colinbatesvt.grocerylist.repository.ListItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CreateListItemController {

    private final ListItemRepository repository;
    private final ModelMapper mapper;

    CreateListItemController(ListItemRepository repository) {
        this.repository = repository;
        this.mapper = new ModelMapper();
    }

    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/listItems")
    List<GroceryListItemDTO> all() {
        List<GroceryListItem> test = repository.findAll();
        System.out.println(test);
        return test.stream()
                .map(item -> mapper.map(item, GroceryListItemDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/createListItems")
    void create() {
        System.out.println("Saving list items...");
        repository.save(new GroceryListItem("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
    }
}
