package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    List<StaffDTO> getAllStaff();
    StaffDTO getStaffById(Integer id);
    StaffDTO saveStaff(StaffDTO staffDTO);
    StaffDTO updateStaff(Integer id, StaffDTO staffDTO);
    void deleteStaff(Integer id);
}
