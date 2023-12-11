package com.github.colinbatesvt.grocerylist.controller;

import com.github.colinbatesvt.grocerylist.model.GroceryList.AddListItemRequest;
import com.github.colinbatesvt.grocerylist.model.GroceryList.CreateGroceryListRequest;
import com.github.colinbatesvt.grocerylist.model.GroceryList.GroceryList;
import com.github.colinbatesvt.grocerylist.model.GroceryList.GroceryListDto;
import com.github.colinbatesvt.grocerylist.repository.GroceryListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class GroceryListController {

    private final GroceryListRepository repository;
    private final ModelMapper mapper;

    GroceryListController(GroceryListRepository repository) {
        this.repository = repository;
        this.mapper = new ModelMapper();
    }

    @GetMapping("/api/getListsForUser/{userName}")
    List<GroceryListDto> getList(@PathVariable String userName) {
        List<GroceryList> userLists = repository.findListsByUserName(userName);
        return userLists.stream().map(list -> {
            return new GroceryListDto(
                    list.getId(),
                    list.getName(),
                    list.getCreatedBy(),
                    list.getCreatedOn(),
                    list.getItems()
            );
        }).collect(Collectors.toList());
    }

    @GetMapping("/api/getList/{id}")
    GroceryListDto getList(@PathVariable Long id) {
        Optional<GroceryList> list = repository.findListById(id);

        if(list.isPresent()) {
            return new GroceryListDto(
                    list.get().getId(),
                    list.get().getName(),
                    list.get().getCreatedBy(),
                    list.get().getCreatedOn(),
                    list.get().getItems()
            );
        } else {
            return new GroceryListDto();
        }
    }

    @PostMapping("/api/createGroceryList")
    void create(@RequestBody CreateGroceryListRequest request) {
        repository.save(new GroceryList(request.getListName(), request.getUserName()));
    }

    @PostMapping("/api/addItem")
    void addItem(@RequestBody AddListItemRequest request) {
        Optional<GroceryList> list = repository.findListById(request.listId);
        if(list.isPresent()) {
            list.get().addItem(request.item);
            repository.save(list.get());
        }
    }
}
