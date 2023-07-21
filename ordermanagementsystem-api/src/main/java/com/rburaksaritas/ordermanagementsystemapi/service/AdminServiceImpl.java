package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.model.Admin;
import com.rburaksaritas.ordermanagementsystemapi.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Admin> getAllAdmins() {
        return null;
    }

    @Override
    public Admin getAdminById(Integer id) {
        return null;
    }

    @Override
    public Admin saveAdmin(Admin adminDTO) {
        return null;
    }

    @Override
    public Admin updateAdmin(Integer id, Admin adminDTO) {
        return null;
    }

    @Override
    public void deleteAdmin(Integer id) {

    }
}
