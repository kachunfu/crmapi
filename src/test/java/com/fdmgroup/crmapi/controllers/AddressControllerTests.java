package com.fdmgroup.crmapi.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.crmapi.models.Address;
import com.fdmgroup.crmapi.services.AddressService;

@WebMvcTest(value = AddressController.class)
public class AddressControllerTests {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean(name = "addressService")
    private AddressService addressService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
    public void givenPropertyObject_whenCreateProperty_thenReturnSavedProperty() throws Exception{
	
    	//given
		Address address = Address.builder()
				.addressLineOne("26 Coventry Road")
				.city("Liverpool")
				.county("South")
				.postcode("L269BJ")
				.country("UK")
				.build();
		
		given(addressService.saveAddress(any(Address.class)))
        	.willAnswer((invocation)-> invocation.getArgument(0));
    	
		//when
		ResultActions response = mockMvc.perform(post("/api/v1/address")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(address)));
    	
		//then
		response.andDo(print())
		         .andExpect(jsonPath("$.postcode",
		                 is(address.getPostcode())));
    	}
	
	//JUnit test for Get address by id 
	//Positive case if the address is not Null
	@Test 
	public void givenAddressId_whenGetAddressById_thenReturnAddressObject() throws Exception{
        // given
		Address address = Address.builder()
				.addressLineOne("26 Coventry Road")
				.city("Liverpool")
				.county("South")
				.postcode("L269BJ")
				.country("UK")
				.build();
        given(addressService.findAddressById(1L)).willReturn(address);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/address/{id}", 1L));

        // then 
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.country", is(address.getCountry())));
    }
	
	//JUnit test for Update address by id 
		@Test
		public void givenUpdatedAddress_whenUpdateAddress_thenReturnUpdateAddressObject() throws Exception{
			//given
			Long id = 1L;
			Address address = Address.builder()
					.addressLineOne("26 Coventry Road")
					.city("Liverpool")
					.county("South")
					.postcode("L269BJ")
					.country("UK")
					.build();
			
			Address updatedAddress = Address.builder()
					.addressLineOne("23 Westridge Road")
					.city("Night")
					.county("Midland")
					.postcode("N98LK5")
					.country("US")
					.build();
			
			 given(addressService.findAddressById(id)).willReturn(address);
	         given(addressService.updateAddress(any(Address.class)))
	                 .willAnswer((invocation)-> invocation.getArgument(0));
			
	         //when
	         ResultActions response = mockMvc.perform(put("/api/v1/address")
	                 	.contentType(MediaType.APPLICATION_JSON)
	                 	.content(objectMapper.writeValueAsString(updatedAddress)));
	         
	         //then
	         response.andExpect(status().isOk())
	         .andDo(print())
	         .andExpect(jsonPath("$.addressLineOne", is(updatedAddress.getAddressLineOne())));	
		}
		
		// JUnit test for delete address when no properties
	    @Test
	    public void givenPropertyId_with_no_address_whenDeleteAddress_thenReturn404() throws Exception{
	        // given 
	        Long id = 1L;

	        willDoNothing().given(addressService).deleteAddressById(id);

	        // when 
	        ResultActions response = mockMvc.perform(delete("/api/v1/address/{id}", id));

	        // then 
	        response.andExpect(status().isNotFound())
	                .andDo(print());
	    }
	
	
	
}
