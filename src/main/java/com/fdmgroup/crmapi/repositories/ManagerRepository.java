package com.fdmgroup.crmapi.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.crmapi.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    /**
     * @param username This is the managers username
     * 
     * Uses a @Query to select the results from the repository that match the username param exactly
     */
    @Query("select manager from Manager manager where manager.username = ?1")
    public Optional<Manager> findByUsername(String username);
}

