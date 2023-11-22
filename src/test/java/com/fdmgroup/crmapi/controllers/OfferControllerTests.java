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

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.crmapi.models.Offer;
import com.fdmgroup.crmapi.services.OfferService;

@WebMvcTest(value = OfferController.class)
public class OfferControllerTests {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean(name = "offerService")
    private OfferService offerService;
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Test
    public void givenOfferObject_whenCreateOffer_thenReturnSavedOffer() throws Exception{
	
    	//given
		Offer offer = Offer.builder()
				.offerPrice(new BigDecimal(4562366))
				.status("On Sale")
				.build();
		
		given(offerService.saveOffer(any(Offer.class)))
        	.willAnswer((invocation)-> invocation.getArgument(0));
    	
		//when
		ResultActions response = mockMvc.perform(post("/api/v1/offers")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(offer)));
    	
		//then
		response.andDo(print())
		         .andExpect(jsonPath("$.status",
		                 is(offer.getStatus())));
    	}
	
	//JUnit test for Get offer by id 
		//Positive case if the offer is not Null
		@Test 
		public void givenOfferId_whenGetOfferById_thenReturnOfferObject() throws Exception{
	        // given
			Offer offer = Offer.builder()
					.id(1L)
					.offerPrice(new BigDecimal(4562366))
					.status("Sold")
					.build();
	        given(offerService.findOfferById(1L)).willReturn(offer);

	        // when
	        ResultActions response = mockMvc.perform(get("/api/v1/offers/{id}", 1L));

	        // then 
	        response.andExpect(status().isOk())
	                .andDo(print())
	                .andExpect(jsonPath("$.status", is(offer.getStatus())));
	    }
		
		//JUnit test for Update address by id 
		@Test
		public void givenUpdatedAddress_whenUpdateAddress_thenReturnUpdateAddressObject() throws Exception{
			//given
			Long id = 1L;
			Offer offer = Offer.builder()
					.offerPrice(new BigDecimal(4562366))
					.status("Sold")
					.build();
					
			Offer updatedOffer = Offer.builder()
					.offerPrice(new BigDecimal(423966))
					.status("On Sale")
					.build();
					
			given(offerService.findOfferById(id)).willReturn(offer);
			given(offerService.updateOffer(any(Offer.class)))
			                 .willAnswer((invocation)-> invocation.getArgument(0));
					
			//when
			 ResultActions response = mockMvc.perform(put("/api/v1/offers")
			                 	.contentType(MediaType.APPLICATION_JSON)
			                 	.content(objectMapper.writeValueAsString(updatedOffer)));
			         
			         //then
			         response.andExpect(status().isOk())
			         .andDo(print())
			         .andExpect(jsonPath("$.status", is(updatedOffer.getStatus())));	
		}
		
		// JUnit test for delete offer when no offer
	    @Test
	    public void givenOfferId_with_no_offer_whenDeleteAddress_thenReturn404() throws Exception{
	        // given 
	        Long id = 1L;

	        willDoNothing().given(offerService).deleteOfferById(id);

	        // when 
	        ResultActions response = mockMvc.perform(delete("/api/v1/offers/{id}", id));

	        // then 
	        response.andExpect(status().isNotFound())
	                .andDo(print());
	    }
	
	
	
}
