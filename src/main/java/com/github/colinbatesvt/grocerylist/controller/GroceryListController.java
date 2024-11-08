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
    List<GroceryListDto> getLists() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<GroceryList> userLists = repository.findListsByUserName(userName);
        return userLists.stream().map(GroceryListDto::new).collect(Collectors.toList());
    }

    @PostMapping("/api/groceryLists/create")
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
            GroceryList updatedList = repository.save(groceryList);
            return new GroceryListDto(updatedList);
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
            groceryList.removeItem(request.getIndex());
            GroceryList updatedList = repository.save(groceryList);
            return new GroceryListDto(updatedList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list found with id="+listId);
        }
    }

    @PostMapping("/api/groceryLists/{listId}/updateItem")
    GroceryListDto updateItem(@PathVariable Long listId, @RequestBody UpdateGroceryListItemRequest request) {
        Optional<GroceryList> groceryListOptional = repository.findListById(listId);
        if(groceryListOptional.isPresent()) {
            GroceryList groceryList = groceryListOptional.get();
            groceryList.setLastUpdatedOn(LocalDateTime.now());
            groceryList.updateItem(request.getIndex(), request.getItem());
            GroceryList updatedList = repository.save(groceryList);
            return new GroceryListDto(updatedList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list found with id="+listId);
        }
    }

    @PostMapping("/api/groceryLists/{listId}/removeCheckedItems")
    GroceryListDto removeCheckedItems(@PathVariable Long listId) {
        Optional<GroceryList> groceryListOptional = repository.findListById(listId);
        if(groceryListOptional.isPresent()) {
            GroceryList groceryList = groceryListOptional.get();
            groceryList.setLastUpdatedOn(LocalDateTime.now());
            groceryList.removeCheckedItems();
            GroceryList updatedList = repository.save(groceryList);
            return new GroceryListDto(updatedList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list found with id="+listId);
        }
    }

    @PostMapping("/api/groceryLists/{listId}/removeAllItems")
    GroceryListDto removeAllItems(@PathVariable Long listId) {
        Optional<GroceryList> groceryListOptional = repository.findListById(listId);
        if(groceryListOptional.isPresent()) {
            GroceryList groceryList = groceryListOptional.get();
            groceryList.setLastUpdatedOn(LocalDateTime.now());
            groceryList.removeAllItems();
            GroceryList updatedList = repository.save(groceryList);
            return new GroceryListDto(updatedList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No grocery list found with id="+listId);
        }
    }
}
