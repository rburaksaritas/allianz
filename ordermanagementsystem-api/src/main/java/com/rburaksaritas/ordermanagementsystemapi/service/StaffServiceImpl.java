package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.dto.StaffDTO;
import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Staff;
import com.rburaksaritas.ordermanagementsystemapi.repository.StaffRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public StaffServiceImpl(StaffRepository staffRepository, ModelMapper modelMapper) {
        this.staffRepository = staffRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<StaffDTO> getAllStaff() {
        List<Staff> staffList = staffRepository.findAll();
        return staffList.stream()
                .map(staff -> modelMapper.map(staff, StaffDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StaffDTO getStaffById(Integer id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Staff", "id", id));
        return modelMapper.map(staff, StaffDTO.class);
    }

    @Override
    public StaffDTO saveStaff(StaffDTO staffDTO) {
        Staff staff = modelMapper.map(staffDTO, Staff.class);
        Staff savedStaff = staffRepository.save(staff);
        return modelMapper.map(savedStaff, StaffDTO.class);
    }

    @Override
    public StaffDTO updateStaff(Integer id, String updatedName, String updatedPhone, String updatedMail, String updatedPassword, String updatedRole) {
        Staff existingStaff = staffRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Staff", "id", id));

        existingStaff.setName(updatedName);
        existingStaff.setPhone(updatedPhone);
        existingStaff.setMail(updatedMail);
        existingStaff.setPassword(updatedPassword);
        existingStaff.setRole(updatedRole);

        Staff updatedStaff = staffRepository.save(existingStaff);
        return modelMapper.map(updatedStaff, StaffDTO.class);
    }

    @Override
    public void deleteStaff(Integer id) {
        Staff existingStaff = staffRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Staff", "id", id));
        staffRepository.deleteById(id);
    }
}
