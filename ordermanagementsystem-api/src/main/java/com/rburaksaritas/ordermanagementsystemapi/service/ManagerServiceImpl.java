package com.rburaksaritas.ordermanagementsystemapi.service;

import com.rburaksaritas.ordermanagementsystemapi.exception.ResourceNotFoundException;
import com.rburaksaritas.ordermanagementsystemapi.model.Manager;
import com.rburaksaritas.ordermanagementsystemapi.repository.ManagerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return managerRepository.findAll();
    }

    @Override
    public Manager getManagerById(Integer id) {
        return managerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
    }

    @Override
    public Manager saveManager(Manager manager) {
        try {
            return managerRepository.save(manager);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to save the manager:" + e);
        }
    }

    @Override
    public Manager updateManager(Integer id, String updatedUsername, String updatedPassword) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));

        try {
            Manager updatedManager = new Manager(id, updatedUsername, updatedPassword, manager.getTimestamp());
            managerRepository.save(updatedManager);
            return updatedManager;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to update the manager: " + e);
        }
    }

    @Override
    public void deleteManager(Integer id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        try {
            managerRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to delete the manager: " + e);
        }
    }
}
