package com.github.colinbatesvt.grocerylist.controller;

import com.github.colinbatesvt.grocerylist.model.GroceryList.*;
import com.github.colinbatesvt.grocerylist.repository.GroceryListRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class GroceryListController {

    private final GroceryListRepository repository;

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
    List<GroceryListDto> getList() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<GroceryList> userLists = repository.findListsByUserName(userName);
        return userLists.stream().map(GroceryListDto::new).collect(Collectors.toList());
    }

    @PutMapping("/api/groceryLists")
    GroceryListDto putList(@RequestBody CreateGroceryListRequest request) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        GroceryList groceryList = new GroceryList(request.getListName(), userName);
        repository.save(groceryList);
        return new GroceryListDto(groceryList);
    }

    @PatchMapping("/api/groceryLists/{id}")
    GroceryListDto updateList(@PathVariable Long id, @RequestBody GroceryList request) {
        Optional<GroceryList> groceryListOptional = repository.findListById(id);
        if(groceryListOptional.isPresent()) {
            GroceryList groceryList = groceryListOptional.get();
            if(request.getName() != null) {
                groceryList.setName(request.getName());
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

    @PostMapping("/api/groceryLists/{listId}/addItem")
    GroceryListDto addItem(@PathVariable Long listId, @RequestBody GroceryListItem item) {
        Optional<GroceryList> groceryListOptional = repository.findListById(listId);
        if(groceryListOptional.isPresent()) {
            GroceryList groceryList = groceryListOptional.get();
            groceryList.setLastUpdatedOn(LocalDateTime.now());
            groceryList.addItem(item);
            repository.save(groceryList);
            return new GroceryListDto(groceryList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list found with id="+listId);
        }
    }

    @PostMapping("/api/groceryLists/{listId}/removeItem")
    GroceryListDto removeItem(@PathVariable Long listId, @RequestBody RemoveGroceryListItemRequest request) {
        Optional<GroceryList> groceryListOptional = repository.findListById(listId);
        if(groceryListOptional.isPresent()) {
            GroceryList groceryList = groceryListOptional.get();
            groceryList.setLastUpdatedOn(LocalDateTime.now());
            groceryList.removeItem(request.index);
            repository.save(groceryList);
            return new GroceryListDto(groceryList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list found with id="+listId);
        }
    }
}
