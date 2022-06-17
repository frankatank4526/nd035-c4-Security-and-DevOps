package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import org.h2.command.ddl.CreateUser;
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


public class CartControllerTests {
    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);

    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @Test
    public void testAddToCartHappy(){
        User u = new User();
        u.setUsername("frank");
        u.setPassword("testPassword");
        u.setCart(new Cart());
        when(userRepository.findByUsername("frank")).thenReturn(u);

        Item i = new Item();
        i.setName("Test Item");
        i.setDescription("This is a Test Item");
        i.setPrice(new BigDecimal("10.0"));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(i));
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("frank");
        cartRequest.setItemId(1L);
        cartRequest.setQuantity(1);

        ResponseEntity<Cart> response = cartController.addTocart(cartRequest);
        Cart c = response.getBody();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Arrays.asList(i), c.getItems());
        assertEquals(new BigDecimal("10.0"), c.getTotal());



    }

    @Test
    public void testAddToCartSad1(){
        User u = new User();
        u.setUsername("frank");
        u.setPassword("testPassword");
        u.setCart(new Cart());
        when(userRepository.findByUsername("frank")).thenReturn(u);

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("frank");
        cartRequest.setItemId(1L);
        cartRequest.setQuantity(1);

        ResponseEntity<Cart> response = cartController.addTocart(cartRequest);
        Cart c = response.getBody();

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

    }

    @Test
    public void testAddToCartSad2(){

        Item i = new Item();
        i.setName("Test Item");
        i.setDescription("This is a Test Item");
        i.setPrice(new BigDecimal("10.0"));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(i));
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setUsername("frank");
        cartRequest.setItemId(1L);
        cartRequest.setQuantity(1);

        ResponseEntity<Cart> response = cartController.addTocart(cartRequest);
        Cart c = response.getBody();

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

    }
    @Test
    public void testRemoveFromCartHappy() {
        User u = new User();
        u.setUsername("frank");
        u.setPassword("testPassword");

        Item i = new Item();
        i.setName("Test Item");
        i.setDescription("This is a Test Item");
        i.setPrice(new BigDecimal("10.0"));

        Cart c = new Cart();
        c.setItems(new ArrayList<Item>(Arrays.asList(i,i,i,i)));
        c.setTotal(new BigDecimal("40.0"));

        u.setCart(c);
        when(userRepository.findByUsername("frank")).thenReturn(u);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(i));

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(1L);
        cartRequest.setUsername("frank");
        cartRequest.setQuantity(2);

        ResponseEntity<Cart> response = cartController.removeFromcart(cartRequest);
        Cart responseCart = response.getBody();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Arrays.asList(i, i), responseCart.getItems());
        assertEquals(new BigDecimal("20.0"), c.getTotal());


    }
    @Test
    public void testRemoveFromCartSad1(){
        User u = new User();
        u.setUsername("frank");
        u.setPassword("testPassword");

        Item i = new Item();
        i.setName("Test Item");
        i.setDescription("This is a Test Item");
        i.setPrice(new BigDecimal("10.0"));

        Cart c = new Cart();
        c.setItems(new ArrayList<Item>(Arrays.asList(i,i,i,i)));
        c.setTotal(new BigDecimal("40.0"));

        u.setCart(c);
        when(userRepository.findByUsername("frank")).thenReturn(u);

        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(1L);
        cartRequest.setUsername("frank");
        cartRequest.setQuantity(2);

        ResponseEntity<Cart> response = cartController.removeFromcart(cartRequest);
        Cart responseCart = response.getBody();

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

    }
    @Test
    public void testRemoveFromCartSad2(){
        User u = new User();
        u.setUsername("frank");
        u.setPassword("testPassword");

        Item i = new Item();
        i.setName("Test Item");
        i.setDescription("This is a Test Item");
        i.setPrice(new BigDecimal("10.0"));

        Cart c = new Cart();
        c.setItems(new ArrayList<Item>(Arrays.asList(i,i,i,i)));
        c.setTotal(new BigDecimal("40.0"));

        u.setCart(c);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(i));
        ModifyCartRequest cartRequest = new ModifyCartRequest();
        cartRequest.setItemId(1L);
        cartRequest.setUsername("frank");
        cartRequest.setQuantity(2);

        ResponseEntity<Cart> response = cartController.removeFromcart(cartRequest);
        Cart responseCart = response.getBody();

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

    }

}
