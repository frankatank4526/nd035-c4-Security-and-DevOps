package com.example.demo.controllers;
import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
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

public class OrderControllerTests {
    private OrderController orderController;
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);

    @Before
    public void setUp(){
        orderController = new OrderController();

        TestUtils.injectObjects(orderController,"orderRepository", orderRepository);
        TestUtils.injectObjects(orderController, "userRepository", userRepository);

    }

    @Test
    public void testSubmit(){
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

        ResponseEntity<UserOrder> response = orderController.submit("frank");
        UserOrder userOrder = response.getBody();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Arrays.asList(i,i,i,i), userOrder.getItems());

    }

    @Test
    public void testGetOrdersForUser(){
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
        UserOrder userOrder1 = UserOrder.createFromCart(c);
        when(userRepository.findByUsername("frank")).thenReturn(u);

        when(orderRepository.findByUser(u)).thenReturn(Arrays.asList(userOrder1));
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("frank");
        List<UserOrder> orderList = response.getBody();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Arrays.asList(userOrder1), orderList);
    }
}
