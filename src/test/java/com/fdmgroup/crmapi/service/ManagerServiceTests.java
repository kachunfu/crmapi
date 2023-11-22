package com.fdmgroup.crmapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import com.fdmgroup.crmapi.models.Manager;
import com.fdmgroup.crmapi.repositories.ManagerRepository;
import com.fdmgroup.crmapi.services.ManagerServiceImplemented;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTests {

	@InjectMocks
	private ManagerServiceImplemented managerService;
	
	@Mock
	private ManagerRepository managerRepository;
	
	private Manager manager;
	
	private Manager manager1;
	
	@BeforeEach
	public void setup() {
		
		manager = Manager .builder()
					.username("johnsmith")
					.password("js564813")
					.build();
		
		manager1 = Manager .builder()
					.username("oliversim")
					.password("os564659")
					.build();
		
	}

	//JUnit test for saveManagerMethod
	@DisplayName("JUnit test for saveManager method")
	@Test
	public void givenManager_Object_whenSaveManager_thenReturnManagerObject() {
	
		//given 	
		given(managerRepository.save(manager)).willReturn(manager);
		
		System.out.println(managerRepository);
		System.out.println(managerService);
		
		//when
		Manager  savedManager = managerService.addManager (manager);
		
		//then
		assertThat(savedManager ).isNotNull();
	}
	
	// JUnit test for getAllManagers method
    @DisplayName("JUnit test for getAllManagers method")
    @Test
    public void givenManagersList_whenGetAllManagers_thenReturnManagersList(){
        // given - precondition or setup
    	
        given(managerRepository.findAll()).willReturn(List.of(manager,manager1));

        // when -  action or the behaviour that we are going test
        List<Manager> managerList = (List<Manager>) managerService.getAllManagers();

        // then - verify the output
        assertThat(managerList).isNotNull();
        assertThat(managerList.size()).isEqualTo(2);
    }
    
    //JUnit test for findManageryByById method
  		@DisplayName("JUnit test for findManageryById method")
  		@Test
  		public void givenAManagerId_whenFindManagerById_thenReturnManagerObject() {
  			
  			//given
  			given(managerRepository.findById(1L)).willReturn(Optional.of(manager));
  			
  			//when
  			Manager savedManager = ( managerService.findManagerById(manager.getUserId()));
  			
  			//then
  			assertThat(savedManager).isNotNull();
  		}
  		
		//JUnit test for updateManager method
		@DisplayName("JUnit test for updateManager method")
		@Test
		public void givenManagerObject_whenUpdateAManager_thenReturnUdatedManager() {
		
			//given
			given(managerRepository.save(manager)).willReturn(manager);
			manager.setUsername("jamiefoy");
			//when
			Manager updatedManager = managerService.updateManager(manager);
			
			//then
			assertThat(updatedManager.getUsername()).isEqualTo("jamiefoy");
		}
		
		@DisplayName("JUnit test for deleteManagerById method")
		@Test
		public void givenManagerId_whenDeleteManager_thenNothing() {
			
			//given
			BDDMockito.willDoNothing().given(managerRepository).deleteById(1L);
			//when
			managerService.deleteManagerById(1L);
			
			//then
			verify(managerRepository, times(1)).deleteById(1L);
			
		}
		
    
    
	
	
	
}
