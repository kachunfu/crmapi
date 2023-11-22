package com.fdmgroup.crmapi.controllers;



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
import com.fdmgroup.crmapi.models.Manager;
import com.fdmgroup.crmapi.repositories.ManagerRepository;
import com.fdmgroup.crmapi.services.ManagerService;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ManagerControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
    private ObjectMapper objectMapper;	
    
	@MockBean(name = "managerService")
    private ManagerService managerService;
	
	@MockBean
	Manager mockManager;
    
    @BeforeEach
    void setup(){
        managerRepository.deleteAll();
    }
	
    @Test
    public void givenManagerObject_whenCreateManager_thenReturnSavedManager() throws Exception{

        // given 
    	Manager manager = Manager.builder()
                .username("kelston")
                .password("smithhhh")
                .build();

        // when 
        ResultActions response = mockMvc.perform(post("/api/v1/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)));

        // then 
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.username",
                        is(manager.getUsername())))
                .andExpect(jsonPath("$.password",
                        is(manager.getPassword())));  
    }
	    
	    //Bad Request with password less than 8 units
	    @Test
	    public void givenAgentObject_whenCreateAgent_with_password_less_than_8_thenReturn400() throws Exception{

	    // given 
	    Manager manager = Manager.builder()
	            .username("kelston")
	            .password("smith")
	            .build();
	
	    // when 
	    ResultActions response = mockMvc.perform(post("/api/v1/managers")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(manager)));
	
	    // then 
	    response.andDo(print()).
	            andExpect(status().isBadRequest());
	}
	    
	 // JUnit test for getAllManagers method
	    @Test
	    public void givenListOfManagers_whenGetAllManagers_thenReturnManagersList() throws Exception{
	    	
	        // given 
	        List<Manager> listOfManagers = new ArrayList<>();
	        listOfManagers.add(Manager.builder().username("Jeffery").password("Dahmerrrr").build());
	        listOfManagers.add(Manager.builder().username("Patrick").password("Batemannnn").build());
	        managerRepository.saveAll(listOfManagers);
	        
	        // when 
	        ResultActions response = mockMvc.perform(get("/api/v1/managers"));

	        // then - verify the output
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.size()",
	                        is( listOfManagers.size())));
	    }
	    
	 // positive scenario - valid manager id
	    // JUnit test for GET manager by id REST API
	    @Test
	    public void givenManagerId_whenGetManagerById_thenReturnManagerObject() throws Exception{
	        // given 
	    	Manager manager = Manager.builder()
	                .username("paulallen")
	                .password("sdgbdwbwbv")
	                .build();
	    	managerRepository.save(manager);

	        // when 
	        ResultActions response = mockMvc.perform(get("/api/v1/managers/{id}", manager.getUserId()));

	        // then 
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.username", is(manager.getUsername())))
	                .andExpect(jsonPath("$.password", is(manager.getPassword())));
	    }
	    
	    // negative scenario - Invalid manager id
	    @Test
	    public void givenInvalidManagerId_whenGetManagerById_thenReturn404() throws Exception{
	        // given - precondition or setup
	    	Long managerId = 1L;
	    	Manager manager = Manager.builder()
	                .username("paulallen")
	                .password("sdgbdwbwbv")
	                .build();
	    	managerRepository.save(manager);

	        // when -  action or the behaviour that we are going test
	        ResultActions response = mockMvc.perform(get("/api/v1/managers/{id}", managerId));

	        // then - verify the output
	        response.andExpect(status().isNotFound())
	                .andDo(print());
	    }
	    
	 // JUnit test for update manager REST API 
	    @Test
	    public void givenUpdatedManager_whenUpdateManager_thenReturnUpdateManagerObject() throws Exception{
	        // given
	    	Manager savedManager = Manager.builder()
	                .username("paulallen")
	                .password("sdgbdwbwbv")
	                .build();
	        managerRepository.save(savedManager);

	        Manager updatedAgent = Manager.builder()
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
	    
	    // JUnit test for delete manager REST API
	    @Test
	    public void givenManagerId_whenDeleteAManager_thenReturn200() throws Exception{
	        // given
	    	Manager savedManager = Manager.builder()
	                .username("paulallen")
	                .password("sdgbdwbwbv")
	                .build();
	    	managerRepository.save(savedManager);

	        // when 
	        ResultActions response = mockMvc.perform(delete("/api/v1/managers/{id}", savedManager.getUserId()));

	        // then
	        response.andExpect(status().isOk())
	                .andDo(print());
	    }
	    
	
	
	
}
