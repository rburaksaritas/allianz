package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.StaffDTO;
import com.rburaksaritas.ordermanagementsystemapi.repository.StaffRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {

    public final StaffRepository staffRepository;
    public final ModelMapper modelMapper;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository, ModelMapper modelMapper) {
        this.staffRepository = staffRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        return null;
    }

    @Override
    public StaffDTO getStaffById(Integer id) {
        return null;
    }

    @Override
    public StaffDTO saveStaff(StaffDTO staffDTO) {
        return null;
    }

    @Override
    public StaffDTO updateStaff(Integer id, StaffDTO staffDTO) {
        return null;
    }

    @Override
    public void deleteStaff(Integer id) {

    }
}
