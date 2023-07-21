package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Manager;
import com.rburaksaritas.ordermanagementsystemapi.repository.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerServiceImplTests {

    private ManagerService managerService;
    private ManagerRepository managerRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        managerRepository = mock(ManagerRepository.class);
        modelMapper = new ModelMapper();
        managerService = new ManagerServiceImpl(managerRepository, modelMapper);
    }

    // ManagerService Tests
    @Test
    public void ManagerService_GetAll_ReturnsAllManagers() {
        // Arrange
        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager(1, "Manager1", "password1", new Date()));
        managers.add(new Manager(2, "Manager2", "password2", new Date()));
        when(managerRepository.findAll()).thenReturn(managers);

        // Act
        List<Manager> result = managerService.getAllManagers();

        // Assert
        assertNotNull(result);
        assertEquals(managers.size(), result.size());
    }

    @Test
    public void ManagerService_GetById_ValidManagerId_ReturnsManager() {
        // Arrange
        int managerId = 1;
        Manager manager = new Manager(managerId, "Manager1", "password1", new Date());
        when(managerRepository.findById(managerId)).thenReturn(Optional.of(manager));

        // Act
        Manager result = managerService.getManagerById(managerId);

        // Assert
        assertNotNull(result);
        assertEquals(manager.getId(), result.getId());
        assertEquals(manager.getUsername(), result.getUsername());
        assertEquals(manager.getPassword(), result.getPassword());
    }

    @Test
    public void ManagerService_GetById_ManagerNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int managerId = 1;
        when(managerRepository.findById(managerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> managerService.getManagerById(managerId));
    }

    @Test
    public void ManagerService_SaveManager_ValidManager_ReturnsSavedManager() {
        // Arrange
        Manager manager = new Manager(1, "Manager1", "password1", new Date());
        when(managerRepository.save(manager)).thenReturn(manager);

        // Act
        Manager result = managerService.saveManager(manager);

        // Assert
        assertNotNull(result);
        assertEquals(manager.getId(), result.getId());
        assertEquals(manager.getUsername(), result.getUsername());
        assertEquals(manager.getPassword(), result.getPassword());
    }

    @Test
    public void ManagerService_SaveManager_DuplicateUsernameThrowsIllegalArgumentException() {
        // Arrange
        Manager manager = new Manager(1, "Manager1", "password1", new Date());
        when(managerRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> managerService.saveManager(manager));
    }

    @Test
    public void ManagerService_UpdateManager_ValidManagerIdAndData_ReturnsUpdatedManager() {
        // Arrange
        int managerId = 1;
        String updatedUsername = "UpdatedManager";
        String updatedPassword = "updatedPassword";
        Manager existingManager = new Manager(managerId, "Manager1", "password1", new Date());
        when(managerRepository.findById(managerId)).thenReturn(Optional.of(existingManager));

        // Act
        Manager result = managerService.updateManager(managerId, updatedUsername, updatedPassword);

        // Assert
        assertNotNull(result);
        assertEquals(managerId, result.getId());
        assertEquals(updatedUsername, result.getUsername());
        assertEquals(updatedPassword, result.getPassword());
    }

    @Test
    public void ManagerService_UpdateManager_ManagerNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int managerId = 1;
        String updatedUsername = "UpdatedManager";
        String updatedPassword = "updatedPassword";
        when(managerRepository.findById(managerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> managerService.updateManager(managerId, updatedUsername, updatedPassword));
    }

    @Test
    public void ManagerService_Delete_ValidManagerId_DeletesManager() {
        // Arrange
        int managerId = 1;
        Manager existingManager = new Manager(managerId, "Manager1", "password1", new Date());
        when(managerRepository.findById(managerId)).thenReturn(Optional.of(existingManager));

        // Act
        assertDoesNotThrow(() -> managerService.deleteManager(managerId));

        // Assert
        verify(managerRepository, times(1)).deleteById(managerId);
    }

    @Test
    public void ManagerService_Delete_ManagerNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int managerId = 1;
        when(managerRepository.findById(managerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> managerService.deleteManager(managerId));
    }
}
