package com.github.colinbatesvt.grocerylist.controller;

import com.github.colinbatesvt.grocerylist.model.GroceryList.*;
import com.github.colinbatesvt.grocerylist.repository.GroceryListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GroceryListControllerTest {

    @Mock
    private GroceryListRepository repository;

    @InjectMocks
    private GroceryListController groceryListController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetListFound() {
        long id = 1L;
        GroceryList groceryList = new GroceryList();
        when(repository.findListById(id)).thenReturn(Optional.of(groceryList));

        GroceryListDto result = groceryListController.getList(id);

        assertNotNull(result);
    }

    @Test
    public void testGetListNotFound() {
        long id = 1L;
        when(repository.findListById(id)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> groceryListController.getList(id));
    }

    @Test
    public void testGetLists() {
        List<GroceryList> userLists = new ArrayList<>();
        String user = "user";
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(user);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(repository.findListsByUserName(user)).thenReturn(userLists);
        List<GroceryListDto> result = groceryListController.getLists();
        assertNotNull(result);
    }

    @Test
    public void testPutList() {
        CreateGroceryListRequest request = new CreateGroceryListRequest();
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("testUser");

        GroceryListDto result = groceryListController.putList(request);

        assertNotNull(result);
    }

    @Test
    public void testUpdateList() {
        long id = 1L;
        GroceryList request = new GroceryList();
        when(repository.findListById(id)).thenReturn(Optional.of(new GroceryList()));

        GroceryListDto result = groceryListController.updateList(id, request);

        assertNotNull(result);
    }

    @Test
    public void testDeleteList() {
        long listId = 1L;

        assertDoesNotThrow(() -> groceryListController.deleteList(listId));
    }

}

