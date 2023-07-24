package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    List<StaffDTO> getAllStaff();
    StaffDTO getStaffById(Integer id);
    StaffDTO saveStaff(StaffDTO staffDTO);
    public StaffDTO updateStaff(Integer id, String updatedName, String updatedPhone, String updatedMail, String updatedPassword, String updatedRole);
    void deleteStaff(Integer id);
}
