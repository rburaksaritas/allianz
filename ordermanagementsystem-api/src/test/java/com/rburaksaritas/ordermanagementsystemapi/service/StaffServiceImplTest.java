package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.StaffDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Staff;
import com.rburaksaritas.ordermanagementsystemapi.repository.StaffRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StaffServiceTests {

    private StaffService staffService;
    private StaffRepository staffRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        staffRepository = mock(StaffRepository.class);
        modelMapper = new ModelMapper();
        staffService = new StaffServiceImpl(staffRepository, modelMapper, null);
    }

    // StaffService Tests
    @Test
    public void StaffService_GetAll_ReturnsAllStaff() {
        // Arrange
        List<Staff> staffList = new ArrayList<>();
        staffList.add(new Staff(1, "Staff 1", "1234567890", "staff1@example.com", "password", "ROLE_ADMIN", new Date()));
        staffList.add(new Staff(2, "Staff 2", "0987654321", "staff2@example.com", "password", "ROLE_USER", new Date()));
        when(staffRepository.findAll()).thenReturn(staffList);

        // Act
        List<StaffDTO> staffDTOList = staffService.getAllStaff();

        // Assert
        assertNotNull(staffDTOList);
        assertEquals(staffList.size(), staffDTOList.size());
    }

    @Test
    public void StaffService_GetById_ValidStaffReturnsStaff() {
        // Arrange
        int staffId = 1;
        Staff staff = new Staff(staffId, "Test Staff", "1234567890", "teststaff@example.com", "password", "ROLE_ADMIN", new Date());
        when(staffRepository.findById(staffId)).thenReturn(Optional.of(staff));

        // Act
        StaffDTO staffDTO = staffService.getStaffById(staffId);

        // Assert
        assertNotNull(staffDTO);
        assertEquals(staff.getId(), staffDTO.getId());
        assertEquals(staff.getName(), staffDTO.getName());
        assertEquals(staff.getMail(), staffDTO.getMail());
    }

    @Test
    public void StaffService_GetById_StaffNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int staffId = 1;
        when(staffRepository.findById(staffId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> staffService.getStaffById(staffId));
    }

    @Test
    public void StaffService_SaveStaff_ValidStaffDTO_ReturnsSavedStaffDTO() {
        // Arrange
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setName("Test Staff");
        staffDTO.setMail("teststaff@example.com");
        Staff staff = modelMapper.map(staffDTO, Staff.class);
        when(staffRepository.save(staff)).thenReturn(staff);

        // Act
        StaffDTO savedStaffDTO = staffService.saveStaff(staffDTO);

        // Assert
        assertNotNull(savedStaffDTO);
        assertEquals(staff.getId(), savedStaffDTO.getId());
        assertEquals(staff.getName(), savedStaffDTO.getName());
        assertEquals(staff.getMail(), savedStaffDTO.getMail());
    }

    @Test
    public void StaffService_UpdateStaff_ValidStaffIdAndData_ReturnsUpdatedStaffDTO() {
        // Arrange
        int staffId = 1;
        String updatedName = "Updated Staff";
        String updatedEmail = "updatedstaff@example.com";
        StaffDTO staffDTO = new StaffDTO(staffId, updatedName, "1234567890", updatedEmail, "password", "ROLE_ADMIN", new Date());
        Staff existingStaff = new Staff(staffId, "Test Staff", "1234567890", "teststaff@example.com", "password", "ROLE_ADMIN", new Date());
        when(staffRepository.findById(staffId)).thenReturn(Optional.of(existingStaff));
        when(staffRepository.save(any())).thenReturn(existingStaff);

        // Act
        StaffDTO updatedStaffDTO = staffService.updateStaff(staffId, updatedName, staffDTO.getPhone(), updatedEmail, staffDTO.getPassword(), staffDTO.getRole());

        // Assert
        assertNotNull(updatedStaffDTO);
        assertEquals(staffId, updatedStaffDTO.getId());
        assertEquals(updatedName, updatedStaffDTO.getName());
        assertEquals(updatedEmail, updatedStaffDTO.getMail());
    }

    @Test
    public void StaffService_UpdateStaff_StaffNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int staffId = 1;
        String updatedName = "Updated Staff";
        String updatedEmail = "updatedstaff@example.com";
        when(staffRepository.findById(staffId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> staffService.updateStaff(staffId, updatedName, "1234567890", updatedEmail, "password", "ROLE_ADMIN"));
    }

    @Test
    public void StaffService_Delete_ValidStaffId_DeletesStaff() {
        // Arrange
        int staffId = 1;
        Staff existingStaff = new Staff(staffId, "Test Staff", "1234567890", "teststaff@example.com", "password", "ROLE_ADMIN", new Date());
        when(staffRepository.findById(staffId)).thenReturn(Optional.of(existingStaff));
        // Act
        assertDoesNotThrow(() -> staffService.deleteStaff(staffId));

        // Assert
        verify(staffRepository, times(1)).deleteById(staffId);
    }

    @Test
    public void StaffService_Delete_StaffNotFoundThrowsResourceNotFoundException() {
        // Arrange
        int staffId = 1;
        when(staffRepository.findById(staffId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> staffService.deleteStaff(staffId));
    }
}
