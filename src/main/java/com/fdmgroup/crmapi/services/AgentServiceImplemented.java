package com.fdmgroup.crmapi.services;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fdmgroup.crmapi.models.Agent;
import com.fdmgroup.crmapi.repositories.AgentRepository;

@Service
public class AgentServiceImplemented implements AgentService {

    private AgentRepository agentRepository;

	public AgentServiceImplemented(AgentRepository agentRepository) {
		this.agentRepository = agentRepository;
	}

    @Override
    public Agent addAgent(Agent agent) {
        // No id provided so will perform insert statement
        return this.agentRepository.save(agent);
    }

    @Override
    public Agent updateAgent(Agent agent) {
        // Id provided so will perform update statement
        return this.agentRepository.save(agent);
    }

    @Override
    public void deleteAgentById(Long id) {
        this.agentRepository.deleteById(id);
    }

    @Override
    public Iterable<Agent> getAllAgents() {
        return this.agentRepository.findAll();
    }

    @Override
    public Agent findAgentById(Long id) {
        return this.agentRepository.findById(id).orElse(null);
    }

    @Override
    public Agent findAgentByUsername(String username) {
        return this.agentRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Iterable<Agent> getAllAgentsByManagerId(Long id) {
        return this.agentRepository.findByManagerId(id).orElse(null);
    }

}
