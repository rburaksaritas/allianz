package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Admin;
import com.rburaksaritas.ordermanagementsystemapi.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
    public Admin saveAdmin(Admin admin) {
        try {
            String hashedPassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(hashedPassword);
            return adminRepository.save(admin);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to save admin: " + e.getMessage());
        }
    }

    @Override
    public Admin updateAdmin(Integer id, Admin adminDTO ) {
        return null;
    }

    @Override
    public void deleteAdmin(Integer id) {
        Admin existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("admin", "id", id));
        adminRepository.deleteById(id);
    }
}
