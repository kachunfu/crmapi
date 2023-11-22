package com.fdmgroup.crmapi.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.crmapi.models.Agent;
import com.fdmgroup.crmapi.repositories.AgentRepository;
import com.fdmgroup.crmapi.services.AgentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AgentControllerTests {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private AgentRepository agentRepository;
	
    @Autowired
    private ObjectMapper objectMapper;	
    
	@MockBean(name = "agentService")
    private AgentService agentService;
    
    
    @MockBean
    Agent mockAgent;
    
    
    @BeforeEach
    void setup(){
        agentRepository.deleteAll();
    }
    
    
    @Test
    public void givenAgentObject_whenCreateAgent_thenReturnSavedAgent() throws Exception{

        // given 
        Agent agent = Agent.builder()
                .username("kelston")
                .password("smithhhh")
                .build();

        // when 
        ResultActions response = mockMvc.perform(post("/api/v1/agents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agent)));

        // then 
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.username",
                        is(agent.getUsername())))
                .andExpect(jsonPath("$.password",
                        is(agent.getPassword())));
              
    }
    
    //Bad Request with password less than 8 units
    @Test
    public void givenAgentObject_whenCreateAgent_with_password_less_than_8_thenReturn400() throws Exception{

        // given 
        Agent agent = Agent.builder()
                .username("kelston")
                .password("smith")
                .build();

        // when 
        ResultActions response = mockMvc.perform(post("/api/v1/agents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agent)));

        // then 
        response.andDo(print()).
                andExpect(status().isBadRequest());
    
    }
    
 // JUnit test for Get All agents REST API
    @Test
    public void givenListOfAgents_whenGetAllAgents_thenReturnAgentsList() throws Exception{
    	
        // given 
        List<Agent> listOfAgents = new ArrayList<>();
        listOfAgents.add(Agent.builder().username("Jeffery").password("Dahmerrrr").build());
        listOfAgents.add(Agent.builder().username("Patrick").password("Batemannnn").build());
        agentRepository.saveAll(listOfAgents);
        
        // when 
        ResultActions response = mockMvc.perform(get("/api/v1/agents"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is( listOfAgents.size())));

    }
    
    // positive scenario - valid agent id
    // JUnit test for GET agent by id REST API
    @Test
    public void givenAgentId_whenGetAgentById_thenReturnAgentObject() throws Exception{
        // given 
        Agent agent = Agent.builder()
                .username("paulallen")
                .password("sdgbdwbwbv")
                .build();
        agentRepository.save(agent);

        // when 
        ResultActions response = mockMvc.perform(get("/api/v1/agents/{id}", agent.getUserId()));

        // then 
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is(agent.getUsername())))
                .andExpect(jsonPath("$.password", is(agent.getPassword())));
    }
    
    // negative scenario - Invalid agent id
    @Test
    public void givenInvalidAgentId_whenGetAgentById_thenReturn404() throws Exception{
        // given - precondition or setup
    	long agentId = 1L;
        Agent agent = Agent.builder()
                .username("paulallen")
                .password("sdgbdwbwbv")
                .build();
        agentRepository.save(agent);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/agents/{id}", agentId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
    
 // JUnit test for update agent REST API - positive scenario
    @Test
    public void givenUpdatedAgent_whenUpdateAgent_thenReturnUpdateAgentObject() throws Exception{
        // given
        Agent savedAgent = Agent.builder()
                .username("paulallen")
                .password("sdgbdwbwbv")
                .build();
        agentRepository.save(savedAgent);

        Agent updatedAgent = Agent.builder()
                .username("brucewayne")
                .password("77777777")
                .build();
       
        // when 
        ResultActions response = mockMvc.perform(put("/api/v1/agents")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedAgent)));

        // then 
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.username", is(updatedAgent.getUsername())))
                .andExpect(jsonPath("$.password", is(updatedAgent.getPassword())));
    }
    
    // JUnit test for delete agent REST API
    @Test
    public void givenAgentId_whenDeleteAgent_thenReturn200() throws Exception{
        // given
        Agent savedAgent = Agent.builder()
                .username("paulallen")
                .password("sdgbdwbwbv")
                .build();
        agentRepository.save(savedAgent);

        // when 
        ResultActions response = mockMvc.perform(delete("/api/v1/agents/{id}", savedAgent.getUserId()));

        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }	
}
