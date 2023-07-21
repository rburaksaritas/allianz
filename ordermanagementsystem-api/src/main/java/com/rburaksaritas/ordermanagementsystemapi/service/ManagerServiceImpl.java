package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.model.Manager;
import com.rburaksaritas.ordermanagementsystemapi.repository.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository managerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository, ModelMapper modelMapper) {
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Manager> getAllManagers() {
        return null;
    }

    @Override
    public Manager getManagerById(Integer id) {
        return null;
    }

    @Override
    public Manager saveManager(Manager managerDTO) {
        return null;
    }

    @Override
    public Manager updateManager(Integer id, Manager managerDTO) {
        return null;
    }

    @Override
    public void deleteManager(Integer id) {

    }
}
