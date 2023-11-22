package com.fdmgroup.crmapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.crmapi.models.Address;
import com.fdmgroup.crmapi.models.Agent;
import com.fdmgroup.crmapi.models.Buyer;
import com.fdmgroup.crmapi.models.Contact;
import com.fdmgroup.crmapi.models.Offer;
import com.fdmgroup.crmapi.models.Property;
import com.fdmgroup.crmapi.models.Seller;
import com.fdmgroup.crmapi.repositories.PropertyRepository;
import com.fdmgroup.crmapi.services.PropertyServiceImp;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTests {

	@InjectMocks
	private PropertyServiceImp propertyService;
	
	@Mock
	private PropertyRepository propertyRepository;
	
	private Property property;
	
	private Property property1;
	
	@BeforeEach
	public void setup() {
		
			Address address = Address.builder()
						.addressLineOne("26 London Road")
						.city("Liverpool")
						.county("North")
						.postcode("K782B63")
						.country("UK")
						.build();
			
			Contact contact = Contact.builder()
						.firstName("John")
						.lastName("Smith")
						.email("js@gmail.com")
						.phone("12345678")
						.build();
			
			Contact contact1 = Contact.builder()
					.firstName("Mary")
					.lastName("King")
					.email("mk@gmail.com")
					.phone("12345678")
					.build();
			
			
			BigDecimal price = BigDecimal.valueOf(500000.00);
			
			Buyer buyer = Buyer.builder()
					.contactDetails(contact1)
					.budget(price)
					.build();
					
			
			Seller seller = Seller.builder()
					.contactDetails(contact)
					.build();
			
			Offer offer = Offer.builder()
						.offerPrice(price)
						.status("Current")
						.build();
			
			List<Offer> listOfOffer = new ArrayList<>();
			listOfOffer.add(offer);
			buyer.setOffers(listOfOffer);
			
			LocalDate postDate = LocalDate.of(2015, Month.FEBRUARY, 11);
	
			property = Property.builder()
					.id(1L)
					.address(address)
					.seller(seller)
					.askingPrice(price)
					.status("Available")
					.dateOfListing(postDate)
					.offers(listOfOffer)
					.build();

			List<Property> listOfProperty = new ArrayList<>() ;
			listOfProperty.add(property);
			

			
			seller.setProperties(listOfProperty);
			
			Agent agent = Agent.builder()
				.username("Ian123")
				.password("xxffgg123")
				.contactDetails(contact)
				.build();
			agent.setProperties(listOfProperty);
			
			
				property1 = Property.builder()
					.id(2L)
					.address(address)
					.seller(seller)
					.askingPrice(price)
					.status("Available")
					.dateOfListing(postDate)
					.offers(listOfOffer)
					.build();
		
	}
	
	
	//JUnit test for saveProperty method
	@DisplayName("JUnit test for saveProperty method")
	@Test
	public void givenPropertyObject_whenSaveProperty_thenReturnPropertyObject() {
	
		//given 
		given(propertyRepository.save(property)).willReturn(property);
		
		//when
		Property savedProperty = propertyService.saveProperty(property);
		
		//then
		assertThat(savedProperty).isNotNull();
		
	}
	
	//JUnit test for findAllProperties method
	@DisplayName("JUnit test for findAllProperties method")
	@Test
	public void givenPropertiesList_whenFindAllProperties_thenReturnPropertiesList() {
		
		//given
		given(propertyRepository.findAll()).willReturn(List.of(property,property1));

		//when
		List<Property> propertyList = (List<Property>) propertyService.findAllProperties();
	
		//then
		assertThat(propertyList).isNotNull();
		assertThat(propertyList.size()).isEqualTo(2);
	}
	
	//JUnit test for findPropertyById method
	@DisplayName("JUnit test for findPropertyById method")
	@Test
	public void givenPropertyId_whenFindPropertyById_thenReturnPropertyObject() {
		
		//given
		given(propertyRepository.findById(1L)).willReturn(Optional.of(property));
		
		//when
		Property savedProperty = ( propertyService.findPropertyById(property.getId()));
		
		//then
		assertThat(savedProperty).isNotNull();
		verify(propertyRepository,times(2)).findById(1L);
	}
	
	//JUnit test for updateProperty method
	@DisplayName("JUnit test for updateProperty method")
	@Test
	public void givenPropertyObject_whenUpdateProperty_thenReturnUdatedProperty() {
	
		//given
		given(propertyRepository.save(property)).willReturn(property);
		property.setStatus("Sold");
		//when
		Property updatedProperty = propertyService.updateProperty(property);
		
		//then
		assertThat(updatedProperty.getStatus()).isEqualTo("Sold");
	}
	
	@DisplayName("JUnit test for deletePropertyById method")
	@Test
	public void givenPropertyId_whenDeleteProperty_thenNothing() {
		
		//given
		BDDMockito.willDoNothing().given(propertyRepository).deleteById(1L);
		//when
		propertyService.deletePropertyById(1L);
		
		//then
		verify(propertyRepository, times(1)).deleteById(1L);
		
	}
}
