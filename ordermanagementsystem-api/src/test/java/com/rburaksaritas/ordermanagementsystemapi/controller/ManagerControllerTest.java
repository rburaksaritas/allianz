package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.model.Manager;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.service.ManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerControllerTests {

    private ManagerController managerController;
    private ManagerService managerService;

    @BeforeEach
    public void setUp() {
        managerService = mock(ManagerService.class);
        managerController = new ManagerController(managerService);
    }

    @Test
    void getAllManagers_ReturnsAllManagersSuccessfully() {
        // Arrange
        List<Manager> expectedManagers = new ArrayList<>();
        expectedManagers.add(new Manager(1, "Manager 1", "password1", null));
        expectedManagers.add(new Manager(2, "Manager 2", "password2", null));
        when(managerService.getAllManagers()).thenReturn(expectedManagers);

        // Act
        ResponseEntity<List<Manager>> responseEntity = managerController.getAllManagers();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedManagers.size(), responseEntity.getBody().size());
    }

    @Test
    void getManagerById_ValidManagerId_ReturnsManagerSuccessfully() {
        // Arrange
        int managerId = 1;
        Manager expectedManager = new Manager(managerId, "Test Manager", "testpassword", null);
        when(managerService.getManagerById(managerId)).thenReturn(expectedManager);

        // Act
        ResponseEntity<Manager> responseEntity = managerController.getManagerById(managerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedManager.getId(), responseEntity.getBody().getId());
        assertEquals(expectedManager.getUsername(), responseEntity.getBody().getUsername());
    }

    @Test
    void getManagerById_ManagerNotFound_ReturnsNotFound() {
        // Arrange
        int managerId = 1;
        when(managerService.getManagerById(managerId)).thenReturn(null);

        // Act
        ResponseEntity<Manager> responseEntity = managerController.getManagerById(managerId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void addManager_ValidManager_ReturnsCreatedManager() {
        // Arrange
        Manager newManager = new Manager(null, "New Manager", "newpassword", null);
        Manager expectedSavedManager = new Manager(1, "New Manager", "newpassword", null);
        when(managerService.saveManager(newManager)).thenReturn(expectedSavedManager);

        // Act
        ResponseEntity<Manager> responseEntity = managerController.addManager(newManager);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedSavedManager.getId(), responseEntity.getBody().getId());
        assertEquals(expectedSavedManager.getUsername(), responseEntity.getBody().getUsername());
    }

    @Test
    void updateManager_ValidManager_ReturnsUpdatedManager() {
        // Arrange
        int managerId = 1;
        Manager updatedManager = new Manager(managerId, "Updated Manager", "updatedpassword", null);
        when(managerService.updateManager(managerId, updatedManager.getUsername(), updatedManager.getPassword()))
                .thenReturn(updatedManager);

        // Act
        ResponseEntity<Manager> responseEntity = managerController.updateManager(managerId, updatedManager.getUsername(), updatedManager.getPassword());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedManager.getId(), responseEntity.getBody().getId());
        assertEquals(updatedManager.getUsername(), responseEntity.getBody().getUsername());
        assertEquals(updatedManager.getPassword(), responseEntity.getBody().getPassword());
    }

    @Test
    void updateManager_ManagerNotFound_ReturnsNotFound() {
        // Arrange
        int managerId = 1;
        Manager updatedManager = new Manager(managerId, "Updated Manager", "updatedpassword", null);
        when(managerService.updateManager(managerId, updatedManager.getUsername(), updatedManager.getPassword()))
                .thenThrow(new ResourceNotFoundException("Manager", "id", managerId));

        // Act
        ResponseEntity<Manager> responseEntity = managerController.updateManager(managerId, updatedManager.getUsername(), updatedManager.getPassword());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteManager_ValidManagerId_ReturnsNoContent() {
        // Arrange
        int managerId = 1;

        // Act
        ResponseEntity<Void> responseEntity = managerController.deleteManager(managerId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteManager_ManagerNotFound_ReturnsNotFound() {
        // Arrange
        int managerId = 1;
        doThrow(new ResourceNotFoundException("Manager", "id", managerId)).when(managerService).deleteManager(managerId);

        // Act
        ResponseEntity<Void> responseEntity = managerController.deleteManager(managerId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
