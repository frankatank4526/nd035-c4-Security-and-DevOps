package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.apache.coyote.Response;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTests {
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp(){
        itemController = new ItemController();

        TestUtils.injectObjects(itemController,"itemRepository", itemRepository);

    }

    @Test
    public void testGetItems(){
        Item i = new Item();
        i.setName("Test Item");
        i.setDescription("This is a Test Item");
        i.setPrice(new BigDecimal("10.0"));

        Item i2 = new Item();
        i2.setName("Test Item 2");
        i2.setDescription("This is a second Test Item");
        i2.setPrice(new BigDecimal("5.0"));

        when(itemRepository.findAll()).thenReturn(Arrays.asList(i, i2));
        ResponseEntity<List<Item>> response = itemController.getItems();
        List<Item> itemList = response.getBody();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Arrays.asList(i, i2), itemList);

    }

    @Test
    public void testGetItemByIdHappy(){
        Item i = new Item();
        i.setName("Test Item");
        i.setDescription("This is a Test Item");
        i.setPrice(new BigDecimal("10.0"));

        Item i2 = new Item();
        i2.setName("Test Item 2");
        i2.setDescription("This is a second Test Item");
        i2.setPrice(new BigDecimal("5.0"));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(i));

        ResponseEntity<Item> response = itemController.getItemById(1L);
        Item returnedItem = response.getBody();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(i, returnedItem);

    }
    @Test
    public void testGetItemByIdSad(){


        ResponseEntity<Item> response = itemController.getItemById(1L);


        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());


    }


    @Test
    public void testGetItemByUsernameHappy(){
        Item i = new Item();
        i.setName("Test Item");
        i.setDescription("This is a Test Item");
        i.setPrice(new BigDecimal("10.0"));

        Item i2 = new Item();
        i2.setName("Test Item 2");
        i2.setDescription("This is a second Test Item");
        i2.setPrice(new BigDecimal("5.0"));

        when(itemRepository.findByName("Test Item")).thenReturn(Arrays.asList(i));

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Test Item");
        List<Item> itemList = response.getBody();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Arrays.asList(i), itemList);


    }

    @Test
    public void testGetItemByUsernameSad(){

        ResponseEntity<List<Item>> response = itemController.getItemsByName("Test Item");
        List<Item> itemList = response.getBody();

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());


    }
}
