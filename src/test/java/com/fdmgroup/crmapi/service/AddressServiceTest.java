package com.fdmgroup.crmapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.crmapi.models.Address;
import com.fdmgroup.crmapi.repositories.AddressRepository;
import com.fdmgroup.crmapi.services.AddressServiceImp;


@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

	@InjectMocks
	private AddressServiceImp addressService;
	
	@Mock
	private AddressRepository addressRepository;
	
	private Address address;
	
	
	@BeforeEach
	public void setup() {
	
	address = Address.builder()
			.id(1L)
			.addressLineOne("26 London Road")
			.city("Liverpool")
			.county("North")
			.postcode("K782B63")
			.country("UK")
			.build();
	}
	
	//JUnit test for saveAddress method
		@DisplayName("JUnit test for saveAddress method")
		@Test
		public void givenAddressObject_whenSaveAddress_thenReturnAddressObject() {
		
			//given 
			given(addressRepository.save(address)).willReturn(address);
			
			//when
			Address savedAddress = addressService.saveAddress(address);
			
			//then
			assertThat(savedAddress).isNotNull();
		}
		
		//JUnit test for findAddressById method
		@DisplayName("JUnit test for findAddressById method")
		@Test
		public void givenAddressId_whenFindAddressById_thenReturnAddressObject() {
			
			//given
			given(addressRepository.findById(1L)).willReturn(Optional.of(address));
			
			//when
			Address savedAddress = ( addressService.findAddressById(address.getId()));
			
			//then
			assertThat(savedAddress).isNotNull();
		}
		
		//JUnit test for updateAddress method
		@DisplayName("JUnit test for updateAddress method")
		@Test
		public void givenAddressObject_whenUpdateAddress_thenReturnUdatedAddress() {
		
			//given
			given(addressRepository.save(address)).willReturn(address);
			address.setCity("Hong Kong");
			//when
			Address updatedAddress = addressService.updateAddress(address);
			
			//then
			assertThat(updatedAddress.getCity()).isEqualTo("Hong Kong");
		}
		
		@DisplayName("JUnit test for deletePropertyById method")
		@Test
		public void givenAddressId_whenDeleteAddress_thenNothing() {
			
			//given
			BDDMockito.willDoNothing().given(addressRepository).deleteById(1L);
			//when
			addressService.deleteAddressById(1L);
			
			//then
			verify(addressRepository, times(1)).deleteById(1L);
		}
		

}
