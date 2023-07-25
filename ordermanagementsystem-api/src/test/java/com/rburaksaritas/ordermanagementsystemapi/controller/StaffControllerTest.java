package com.rburaksaritas.ordermanagementsystemapi.controller;

import com.rburaksaritas.ordermanagementsystemapi.dto.StaffDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.service.StaffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffControllerTests {

    private StaffController staffController;
    private StaffService staffService;

    @BeforeEach
    public void setUp() {
        staffService = mock(StaffService.class);
        staffController = new StaffController(staffService);
    }

    @Test
    void getAllStaff_ReturnsAllStaffSuccessfully() {
        // Arrange
        List<StaffDTO> expectedStaffList = new ArrayList<>();
        expectedStaffList.add(new StaffDTO(1, "John Doe", "123456789", "john@example.com", "password", "admin", new Date()));
        expectedStaffList.add(new StaffDTO(2, "Jane Smith", "987654321", "jane@example.com", "password", "staff", new Date()));
        when(staffService.getAllStaff()).thenReturn(expectedStaffList);

        // Act
        ResponseEntity<List<StaffDTO>> responseEntity = staffController.getAllStaff();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedStaffList.size(), responseEntity.getBody().size());
    }

    @Test
    void getStaffById_ValidStaffId_ReturnsStaffSuccessfully() {
        // Arrange
        int staffId = 1;
        StaffDTO expectedStaff = new StaffDTO(staffId, "John Doe", "123456789", "john@example.com", "password", "admin", new Date());
        when(staffService.getStaffById(staffId)).thenReturn(expectedStaff);

        // Act
        ResponseEntity<StaffDTO> responseEntity = staffController.getStaffById(staffId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedStaff.getId(), responseEntity.getBody().getId());
        assertEquals(expectedStaff.getName(), responseEntity.getBody().getName());
    }

    @Test
    void getStaffById_StaffNotFound_ReturnsNotFound() {
        // Arrange
        int staffId = 1;
        when(staffService.getStaffById(staffId)).thenReturn(null);

        // Act
        ResponseEntity<StaffDTO> responseEntity = staffController.getStaffById(staffId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void addStaff_ValidStaff_ReturnsCreatedStaff() {
        // Arrange
        StaffDTO newStaff = new StaffDTO(null, "John Doe", "123456789", "john@example.com", "password", "admin", null);
        StaffDTO expectedSavedStaff = new StaffDTO(1, "John Doe", "123456789", "john@example.com", "password", "admin", new Date());
        when(staffService.saveStaff(newStaff)).thenReturn(expectedSavedStaff);

        // Act
        ResponseEntity<StaffDTO> responseEntity = staffController.addStaff(newStaff);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(expectedSavedStaff.getId(), responseEntity.getBody().getId());
        assertEquals(expectedSavedStaff.getName(), responseEntity.getBody().getName());
    }

    @Test
    void updateStaff_ValidStaff_ReturnsUpdatedStaff() {
        // Arrange
        int staffId = 1;
        String updatedName = "John Smith";
        String updatedPhone = "987654321";
        String updatedMail = "john.smith@example.com";
        String updatedPassword = "newpassword";
        String updatedRole = "staff";
        StaffDTO updatedStaff = new StaffDTO(staffId, updatedName, updatedPhone, updatedMail, updatedPassword, updatedRole, new Date());
        when(staffService.updateStaff(staffId, updatedName, updatedPhone, updatedMail, updatedPassword, updatedRole))
                .thenReturn(updatedStaff);

        // Act
        ResponseEntity<StaffDTO> responseEntity = staffController.updateStaff(staffId, updatedName, updatedPhone, updatedMail, updatedPassword, updatedRole);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedStaff.getId(), responseEntity.getBody().getId());
        assertEquals(updatedStaff.getName(), responseEntity.getBody().getName());
    }

    @Test
    void updateStaff_StaffNotFound_ReturnsNotFound() {
        // Arrange
        int staffId = 1;
        String updatedName = "John Smith";
        String updatedPhone = "987654321";
        String updatedMail = "john.smith@example.com";
        String updatedPassword = "newpassword";
        String updatedRole = "staff";
        when(staffService.updateStaff(staffId, updatedName, updatedPhone, updatedMail, updatedPassword, updatedRole))
                .thenThrow(new ResourceNotFoundException("Staff", "id", staffId));

        // Act
        ResponseEntity<StaffDTO> responseEntity = staffController.updateStaff(staffId, updatedName, updatedPhone, updatedMail, updatedPassword, updatedRole);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void deleteStaff_ValidStaffId_ReturnsNoContent() {
        // Arrange
        int staffId = 1;

        // Act
        ResponseEntity<Void> responseEntity = staffController.deleteStaff(staffId);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void deleteStaff_StaffNotFound_ReturnsNotFound() {
        // Arrange
        int staffId = 1;
        doThrow(new ResourceNotFoundException("Staff", "id", staffId)).when(staffService).deleteStaff(staffId);

        // Act
        ResponseEntity<Void> responseEntity = staffController.deleteStaff(staffId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
