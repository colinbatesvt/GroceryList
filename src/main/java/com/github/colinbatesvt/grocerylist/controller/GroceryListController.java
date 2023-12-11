package com.github.colinbatesvt.grocerylist.controller;

import com.github.colinbatesvt.grocerylist.model.GroceryList.*;
import com.github.colinbatesvt.grocerylist.repository.GroceryListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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

    @GetMapping("/api/groceryLists/{id}")
    GroceryListDto getList(@PathVariable Long id) {
        Optional<GroceryList> list = repository.findListById(id);

        if(list.isPresent()) {
            return new GroceryListDto(list.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @GetMapping("/api/groceryLists")
    List<GroceryListDto> getList(@RequestParam Optional<String> userName) {
        if(userName.isPresent()) {
            List<GroceryList> userLists = repository.findListsByUserName(userName.get());
            return userLists.stream().map(GroceryListDto::new).collect(Collectors.toList());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PutMapping("/api/groceryLists")
    GroceryListDto putList(@RequestBody CreateGroceryListRequest request) {
        GroceryList groceryList = new GroceryList(request.getListName(), request.getUserName());
        repository.save(groceryList);
        return new GroceryListDto(groceryList);
    }

    @PatchMapping("/api/groceryLists/{id}")
    GroceryListDto updateList(@PathVariable Long id, @RequestBody UpdateGroceryListRequest request) {
        Optional<GroceryList> groceryListOptional = repository.findListById(id);
        if(groceryListOptional.isPresent()) {
            GroceryList groceryList = groceryListOptional.get();
            if(request.getName() != null) {
                groceryList.setName(request.getName());
            }
            if(request.getItems() != null) {
                groceryList.setItems(request.getItems());
            }
            groceryList.setLastUpdatedOn(LocalDateTime.now());
            repository.save(groceryList);
            return new GroceryListDto(groceryList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list found with id="+id);
        }
    }

    @DeleteMapping("/api/groceryLists/{listId}")
    void deleteList(@PathVariable Long listId) {
        repository.deleteById(listId);
    }
}
