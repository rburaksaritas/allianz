package com.rburaksaritas.booklendingapi.service;

import com.rburaksaritas.booklendingapi.model.User;
import com.rburaksaritas.booklendingapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void UserService_Get_ValidMailReturnsUser() {
        User user = new User();
        user.setMail("test@mail.com");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        User result = userService.getUser("test@mail.com");

        assertNotNull(result);
        assertEquals("test@mail.com", result.getMail());
    }

    @Test
    void UserService_Get_InvalidMailReturnsNull() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        User result = userService.getUser("invalid@mail.com");

        assertNull(result);
    }

    @Test
    void UserService_Save_ValidUserReturnsUser() {
        User user = new User();
        user.setMail("test@mail.com");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.saveUser(user);

        assertNotNull(result);
        assertEquals("test@mail.com", result.getMail());
    }

    @Test
    void UserService_Update_ValidMailUpdatesUser() {
        User user = new User();
        user.setMail("test@mail.com");
        user.setPassword("password");
        user.setActive(true);

        User updatedUser = new User();
        updatedUser.setMail("test@mail.com");
        updatedUser.setPassword("new_password");
        updatedUser.setActive(false);

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser("test@mail.com", updatedUser);

        assertNotNull(result);
        assertEquals("new_password", result.getPassword());
        assertFalse(result.isActive());
    }

    @Test
    void UserService_Delete_ValidMailReturnsTrue() {
        User user = new User();
        user.setMail("test@mail.com");

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));

        boolean result = userService.deleteUser("test@mail.com");

        assertTrue(result);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void UserService_Delete_InvalidMailReturnsFalse() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        boolean result = userService.deleteUser("invalid@mail.com");

        assertFalse(result);
        verify(userRepository, never()).delete(any(User.class));
    }
}
