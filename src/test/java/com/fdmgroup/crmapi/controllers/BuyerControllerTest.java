package com.fdmgroup.crmapi.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
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
import com.fdmgroup.crmapi.models.Buyer;
import com.fdmgroup.crmapi.repositories.BuyerDAO;
import com.fdmgroup.crmapi.services.BuyerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BuyerControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private BuyerDAO buyerRepository;
	
    @Autowired
    private ObjectMapper objectMapper;	
    
	@MockBean(name = "buyerService")
    private BuyerService buyerService;
    
    @MockBean
    Buyer mockBuyer;
    
    @BeforeEach
    void setup(){
        buyerRepository.deleteAll();
    }
	
    @Test
    public void givenBuyerObject_whenCreateBuyer_thenReturnSavedBuyer() throws Exception{

        // given 
    	Buyer buyer = Buyer.builder()
                .budget(new BigDecimal(165451))
                .build();

        // when 
        ResultActions response = mockMvc.perform(post("/api/v1/buyers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyer)));

        // then 
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.budget",
                        is(buyer.getBudget().intValue())));
    }
        // JUnit test for Get All agents REST API
        @Test
        public void givenListOfBuyers_whenGetAllBuyers_thenReturnBuyersList() throws Exception{
        	
            // given 
            List<Buyer> listOfBuyers = new ArrayList<>();
            listOfBuyers.add(Buyer.builder().budget(new BigDecimal(165451)).build());
            listOfBuyers.add(Buyer.builder().budget(new BigDecimal(5916525)).build());
            buyerRepository.saveAll(listOfBuyers);
            
            // when 
            ResultActions response = mockMvc.perform(get("/api/v1/buyers"));

            // then - verify the output
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.size()",
                            is( listOfBuyers.size())));
        }
        
        // positive scenario - valid buyer id
        // JUnit test for GET buyer by id REST API
        @Test
        public void givenBuyerId_whenGetBuyerById_thenReturnBuyerObject() throws Exception{
            // given 

        	Buyer buyer= Buyer.builder()
        			.budget(new BigDecimal(444435))
        			.build();
            buyerRepository.save(buyer);

            // when 
            ResultActions response = mockMvc.perform(get("/api/v1/buyers/{id}", buyer.getId()));

            // then 
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.budget",is(buyer.getBudget().setScale(1))));
        }
        
     // JUnit test for update buyer REST API 
        @Test
        public void givenUpdatedBuyer_whenBuyerAgent_thenReturnBuyerAgentObject() throws Exception{
            // given
        	Buyer buyer= Buyer.builder()
        			.budget(new BigDecimal(555324))
        			.build();
            buyerRepository.save(buyer);

            Buyer updatedBuyer = Buyer.builder()
           			.budget(new BigDecimal(444435))
        			.build();
           
            // when 
            ResultActions response = mockMvc.perform(put("/api/v1/buyers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedBuyer)));

            // then 
            response.andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.budget", is(updatedBuyer.getBudget().intValue())));
        }
        
     // JUnit test for delete buyer REST API
        @Test
        public void givenBuyerId_whenDeleteBuyer_thenReturn200() throws Exception{
            // given
        	Buyer savedBuyer = Buyer.builder()
          			.budget(new BigDecimal(444435))
        			.build();
            buyerRepository.save(savedBuyer);

            // when 
            ResultActions response = mockMvc.perform(delete("/api/v1/buyers/{id}", savedBuyer.getId()));

            // then
            response.andExpect(status().isOk())
                    .andDo(print());
        }
               
    }
    
    
	

