package com.rburaksaritas.booklendingapi.controller;

import com.rburaksaritas.booklendingapi.exception.ResourceNotFoundException;
import com.rburaksaritas.booklendingapi.model.User;
import com.rburaksaritas.booklendingapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void UserController_Get_ReturnsUser() {
        User user = new User();
        user.setMail("test@mail.com");
        user.setPassword("password");
        user.setActive(true);
        when(userService.getUser(anyString())).thenReturn(user);
        ResponseEntity<String> response = userController.getUser("test@mail.com");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void UserController_Add_ReturnsUser() {
        User user = new User();
        user.setMail("test@mail.com");
        user.setPassword("password");
        user.setActive(true);
        when(userService.saveUser(any(User.class))).thenReturn(user);
        ResponseEntity<String> response = userController.addUser(user);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void UserController_Update_ReturnsUser() {
        User user = new User();
        user.setMail("test@mail.com");
        user.setPassword("password");
        user.setActive(true);
        when(userService.updateUser(anyString(), any(User.class))).thenReturn(user);
        ResponseEntity<String> response = userController.updateUser("test@mail.com", user);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void UserController_Delete_ReturnsOk() {
        when(userService.deleteUser(anyString())).thenReturn(true);
        ResponseEntity<String> response = userController.deleteUser("test@mail.com");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void UserController_Get_UserDoesNotExist_ThrowsException() {
        when(userService.getUser(anyString())).thenThrow(new ResourceNotFoundException("user", "mail", anyString()));
        ResponseEntity<String> response = userController.getUser("test@mail.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void UserController_Add_NullUser_ThrowsException() {
        when(userService.saveUser(null)).thenThrow(new IllegalArgumentException("User cannot be null"));
        ResponseEntity<String> response = userController.addUser(null);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void UserController_Update_UserDoesNotExist_ThrowsException() {
        User user = new User();
        user.setMail("test@mail.com");
        user.setPassword("password");
        user.setActive(true);
        when(userService.updateUser(anyString(), any(User.class))).thenThrow(new ResourceNotFoundException("user", "mail", anyString()));
        ResponseEntity<String> response = userController.updateUser("test@mail.com", user);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void UserController_Delete_UserDoesNotExist_ThrowsException() {
        when(userService.deleteUser(anyString())).thenThrow(new ResourceNotFoundException("user", "mail", anyString()));
        ResponseEntity<String> response = userController.deleteUser("test@mail.com");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
