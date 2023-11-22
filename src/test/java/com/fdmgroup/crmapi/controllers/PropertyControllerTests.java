package com.fdmgroup.crmapi.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.crmapi.models.Property;
import com.fdmgroup.crmapi.services.PropertyService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;



@WebMvcTest(value = PropertyController.class)
public class PropertyControllerTests {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean(name = "propertyService")
    private PropertyService propertyService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
    public void givenPropertyObject_whenCreateProperty_thenReturnSavedProperty() throws Exception{
	
    	//given
		Property property = Property.builder()
				.status("Available")
				.build();
		
		given(propertyService.saveProperty(any(Property.class)))
        	.willAnswer((invocation)-> invocation.getArgument(0));
    	
		//when
		ResultActions response = mockMvc.perform(post("/api/v1/properties")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(property)));
    	
		//then
		response.andDo(print())
		         .andExpect(jsonPath("$.status",
		                 is(property.getStatus())));
    	}
	
	//JUnit test for Get All properties 
	@Test
	public void givenListOfProperties_whenFindAllProperties_thenReturnPropertiesList()throws Exception{
		
		//given
		List<Property> listOfProperties = new ArrayList<>();
		 listOfProperties.add(Property.builder().status("For Sale").build());
	     listOfProperties.add(Property.builder().status("Sold").build());
	     given(propertyService.findAllProperties()).willReturn(listOfProperties);
	     
	     //when
	     ResultActions response = mockMvc.perform(get("/api/v1/properties"));
		
	     //then
	     response.andExpect(status().isOk())
		         .andDo(print())
		         .andExpect(jsonPath("$.size()",
		                 is(listOfProperties.size())));
	}
	
	//JUnit test for Get property by id 
	//Positive case if the property is not Null
	@Test 
	public void givenPropertyId_whenGetPropertyById_thenReturnPropertyObject() throws Exception{
        // given
		Property property = Property.builder()
                .status("Let")
                .build();
        given(propertyService.findPropertyById(1L)).willReturn(property);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/properties/{id}", 1L));

        // then 
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.status", is(property.getStatus())));
        
        
   
    }
	//JUnit test for Update property by id 
	@Test
	public void givenUpdatedProperty_whenUpdateProperty_thenReturnUpdatePropertyObject() throws Exception{
		//given
		Long id = 1L;
		Property property = Property.builder()
                .status("Let")
                .build();
		
		Property updatedProperty = Property.builder()
				.status("Sold")
				.build();
		
		 given(propertyService.findPropertyById(id)).willReturn(property);
         given(propertyService.updateProperty(any(Property.class)))
                 .willAnswer((invocation)-> invocation.getArgument(0));
		
         //when
         ResultActions response = mockMvc.perform(put("/api/v1/properties")
                 	.contentType(MediaType.APPLICATION_JSON)
                 	.content(objectMapper.writeValueAsString(updatedProperty)));
         
         //then
         response.andExpect(status().isOk())
         .andDo(print())
         .andExpect(jsonPath("$.status", is(updatedProperty.getStatus())));	
	}
	
	// JUnit test for delete property when no properties
    @Test
    public void givenPropertyId_with_no_properties_whenDeleteProperty_thenReturn404() throws Exception{
        // given 
        Long id = 1L;

        willDoNothing().given(propertyService).deletePropertyById(id);

        // when 
        ResultActions response = mockMvc.perform(delete("/api/v1/properties/{id}", id));

        // then 
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
    
	
	
}

