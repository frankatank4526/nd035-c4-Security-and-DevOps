package com.example.demo.controllers;


import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.h2.command.ddl.CreateUser;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTests {
    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }
    @Test
    public void create_user_happy_path() throws Exception{
        when(encoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("frank");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("frank", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());


    }

    @Test
    public void testCreateUserSadPath(){
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("frank");
        r.setPassword("badPas");
        r.setConfirmPassword("badPas");

        ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

    }
    @Test
    public void testFindByIdHappyPath(){

        User u = new User();
        u.setUsername("frank");
        u.setPassword("testPassword");
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        ResponseEntity<User> response = userController.findById(1L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("frank", response.getBody().getUsername());


    }
    @Test
    public void testFindByIdSadPath(){

        ResponseEntity<User> response = userController.findById(1L);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());



    }
    @Test
    public void testFindByUsernameHappyPath(){

        User u = new User();
        u.setUsername("frank");
        u.setPassword("testPassword");

        when(userRepository.findByUsername("frank")).thenReturn(u);
        ResponseEntity<User> response = userController.findByUserName("frank");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("frank", response.getBody().getUsername());


    }

    @Test
    public void testFindByUsernameSadPath(){

        ResponseEntity<User> response = userController.findByUserName("frank");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());



    }


}
