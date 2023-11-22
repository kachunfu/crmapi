package com.fdmgroup.crmapi.services;

import com.fdmgroup.crmapi.models.Manager;

public interface ManagerService {
    
    Manager addManager(Manager manager);

	Manager updateManager(Manager manager);

	void deleteManagerById(Long id);

    Iterable<Manager> getAllManagers();

    Manager findManagerById(Long id);

    Manager findManagerByUsername(String username);
}
