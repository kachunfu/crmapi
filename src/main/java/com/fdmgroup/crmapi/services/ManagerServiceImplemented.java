package com.fdmgroup.crmapi.services;

import org.springframework.stereotype.Service;

import com.fdmgroup.crmapi.models.Manager;
import com.fdmgroup.crmapi.repositories.ManagerRepository;

@Service
public class ManagerServiceImplemented implements ManagerService {

    private ManagerRepository managerRepository;

	public ManagerServiceImplemented(ManagerRepository managerRepository) {
		this.managerRepository = managerRepository;
	}

    @Override
    public Manager addManager(Manager manager) {
        // No id provided so will perform insert statement
        return this.managerRepository.save(manager);
    }

    @Override
    public Manager updateManager(Manager manager) {
        // Id provided so will perform update statement
        return this.managerRepository.save(manager);
    }

    @Override
    public void deleteManagerById(Long id) {
        this.managerRepository.deleteById(id);
    }

    @Override
    public Iterable<Manager> getAllManagers() {
        return this.managerRepository.findAll();
    }

    @Override
    public Manager findManagerById(Long id) {
        return this.managerRepository.findById(id).orElse(null);
    }

    @Override
    public Manager findManagerByUsername(String username) {
        return this.managerRepository.findByUsername(username).orElse(null);
    }
    
}
