package com.fdmgroup.crmapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.crmapi.models.Offer;
import com.fdmgroup.crmapi.repositories.OfferRepository;
import com.fdmgroup.crmapi.services.OfferServiceImp;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTests {

		@InjectMocks
		private OfferServiceImp offerService;
		
		@Mock
		private OfferRepository offerRepository;
		
		private Offer offer;
		
		private Offer offer1;
		
		
		@BeforeEach
		public void setup() {
		
			offer = Offer.builder()
				.id(1L)
				.offerPrice(new BigDecimal(4562366))
				.status("On Sale")
				.build();
			
			offer1 = Offer.builder()
				.offerPrice(new BigDecimal(786873))
				.status("Sold")
				.build();
		}
		
		//JUnit test for saveOffer method
		@DisplayName("JUnit test for saveOffer method")
		@Test
		public void givenOfferObject_whenSaveOffer_thenReturnOfferObject() {
				
		//given 
		given(offerRepository.save(offer)).willReturn(offer);
					
		//when
		Offer savedOffer = offerService.saveOffer(offer);
					
		//then
		assertThat(savedOffer).isNotNull();
		}
		
		//JUnit test for findAllOffers method
		@DisplayName("JUnit test for findAllOffers method")
		@Test
		public void givenOffersList_whenFindAllOffers_thenReturnOffersList() {
			
			 //given
			given(offerRepository.findAll()).willReturn(List.of(offer,offer1));

			//when
			List<Offer> offerList = (List<Offer>) offerService.findAllOffers();
		
			//then
			assertThat(offerList).isNotNull();
			assertThat(offerList.size()).isEqualTo(2);
		}
		
		//JUnit test for findOfferById method
		@DisplayName("JUnit test for findOfferById method")
		@Test
		public void givenOfferId_whenFindOfferById_thenReturnOfferObject() {
					
			//given
			given(offerRepository.findById(1L)).willReturn(Optional.of(offer));
					
			//when
			Offer savedOffer = (offerService.findOfferById(offer.getId()));
					
			//then
			assertThat(savedOffer).isNotNull();
		}
		
		//JUnit test for updateOffer method
		@DisplayName("JUnit test for updateOffer method")
		@Test
		public void givenOfferObject_whenUpdateOffer_thenReturnUdatedOffer() {
				
			//given
			given(offerRepository.save(offer)).willReturn(offer);
			offer.setStatus("Test");
			//when
			Offer updatedOffer = offerService.updateOffer(offer);
					
			//then
			assertThat(updatedOffer.getStatus()).isEqualTo("Test");
		}
		
		@DisplayName("JUnit test for deleteOfferById method")
		@Test
		public void givenOfferId_whenDeleteOffer_thenNothing() {
			
			//given
			BDDMockito.willDoNothing().given(offerRepository).deleteById(1L);
			//when
			offerService.deleteOfferById(1L);
			
			//then
			verify(offerRepository, times(1)).deleteById(1L);
		}	
		
}	
		

