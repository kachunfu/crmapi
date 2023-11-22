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

import com.fdmgroup.crmapi.models.Buyer;
import com.fdmgroup.crmapi.models.Contact;
import com.fdmgroup.crmapi.repositories.BuyerDAO;
import com.fdmgroup.crmapi.services.BuyerServiceImp;

@ExtendWith(MockitoExtension.class)
public class BuyerServiceTests {
	
	@InjectMocks
	private BuyerServiceImp buyerService;
	
	@Mock
	private BuyerDAO buyerRepository;
	
	private Buyer buyer;
	
	private Buyer buyer1;
	
	private Contact contact;
	
	private Contact contact1;
	
	@BeforeEach
	public void setup() {
		
		contact = Contact.builder()
						.firstName("Jeffrey")
						.lastName("Dahmer")
						.email("jeffreydahmer@gmail.com")
						.phone("07492701013")
						.build();
		
		contact1 = Contact.builder()
						.firstName("Bruce")
						.lastName("Wayne")
						.email("brucewayne@gmail.com")
						.phone("07472764862")
						.build();
		
		buyer = Buyer.builder()
					.contactDetails(contact)
					.budget(new BigDecimal(852215.00))
					.build();
		
		buyer = Buyer.builder()
					.contactDetails(contact1)
					.budget(new BigDecimal(9999999999.00))
					.build();
		
	}
	//JUnit test for saveBuyerMethod
			@DisplayName("JUnit test for saveBuyer method")
			@Test
			public void givenBuyerObject_whenSaveBuyer_thenReturnBuyerObject() {
			
				//given 	
				given(buyerRepository.save(buyer)).willReturn(buyer);
				
				System.out.println(buyerRepository);
				System.out.println(buyerService);
				
				//when
				Buyer savedBuyer= buyerService.createBuyer(buyer);
				
				//then
				assertThat(savedBuyer).isNotNull();
			}
			
			// JUnit test for getAllBuyers method
		    @DisplayName("JUnit test for getAllBuyers method")
		    @Test
		    public void givenBuyersList_whenGetAllBuyers_thenReturnBuyersList(){
		    	
		        // given 
		        given(buyerRepository.findAll()).willReturn(List.of(buyer,buyer1));

		        // when 
		        List<Buyer> buyerList = (List<Buyer>)buyerService.getAllBuyers();

		        // then 
		        assertThat(buyerList).isNotNull();
		        assertThat(buyerList.size()).isEqualTo(2);
		    }
		    
			//JUnit test for findBuyerByById method
			@DisplayName("JUnit test for findBuyeryById method")
			@Test
			public void givenBuyerId_whenFindBuyerById_thenReturnBuyerObject() {
				
				//given
				given(buyerRepository.findById(1L)).willReturn(Optional.of(buyer));
				
				//when
				Buyer savedBuyer = ( buyerService.findBuyerById(buyer.getId()));
				
				//then
				assertThat(savedBuyer).isNotNull();
			}
			
			//JUnit test for updateBuyer method
			@DisplayName("JUnit test for updateBuyer method")
			@Test
			public void givenBuyerObject_whenUpdateBuyer_thenReturnUpdatedBuyer() {
			
				//given
				given(buyerRepository.save(buyer)).willReturn(buyer);
				buyer.setBudget(new BigDecimal(100056.00));
				//when
				Buyer updatedBuyer = buyerService.updateBuyer(buyer);
				
				//then
				assertThat(updatedBuyer.getBudget()).isEqualTo(new BigDecimal(100056.00));
			}
			

			@DisplayName("JUnit test for deleteBuyerById method")
			@Test
			public void givenBuyerId_whenDeleteBuyer_thenNothing() {
				
				//given
				BDDMockito.willDoNothing().given(buyerRepository).deleteById(1L);
				//when
				buyerService.deleteBuyerById(1L);
				
				//then
				verify(buyerRepository, times(1)).deleteById(1L);
				
			}
			
}
