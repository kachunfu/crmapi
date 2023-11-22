package com.fdmgroup.crmapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.crmapi.models.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    /**
     * @param username This is the agents username
     * 
     * Uses a @Query to select the results from the repository that match the username param exactly
     */
    // @Query("select agent from Agent agent where agent.username LIKE %?1%")
    @Query("select agent from Agent agent where agent.username = ?1")
    public Optional<Agent> findByUsername(String username);

    /**
     * @param managerId This is the manager id
     * 
     * Uses a @Query to select the results from the repository that match the managerId param exactly
     */
    @Query("select agent from Agent agent where agent.manager.id = ?1")
    public Optional<List<Agent>> findByManagerId(Long managerId);
}

