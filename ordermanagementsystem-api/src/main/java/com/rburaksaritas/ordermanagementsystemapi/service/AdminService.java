package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.model.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> getAllAdmins();
    Admin getAdminById(Integer id);
    Admin saveAdmin(Admin adminDTO);
    Admin updateAdmin(Integer id, Admin adminDTO);
    void deleteAdmin(Integer id);
}
