package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.model.Manager;

import java.util.List;

public interface ManagerService {
    List<Manager> getAllManagers();
    Manager getManagerById(Integer id);
    Manager saveManager(Manager managerDTO);
    Manager updateManager(Integer id, String updatedUsername, String updatedPassword);
    void deleteManager(Integer id);
}
