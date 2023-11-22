package com.fdmgroup.crmapi.services;


import com.fdmgroup.crmapi.models.Agent;


public interface AgentService {
    
    Agent addAgent(Agent agent);

	Agent updateAgent(Agent Agent);

	void deleteAgentById(Long id);

    Iterable<Agent> getAllAgents();

    Agent findAgentById(Long id);

    Agent findAgentByUsername(String username);

    Iterable<Agent> getAllAgentsByManagerId(Long id);

    
}
