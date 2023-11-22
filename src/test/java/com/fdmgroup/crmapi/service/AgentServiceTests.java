package com.fdmgroup.crmapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import com.fdmgroup.crmapi.models.Agent;
import com.fdmgroup.crmapi.repositories.AgentRepository;
import com.fdmgroup.crmapi.services.AgentServiceImplemented;

@ExtendWith(MockitoExtension.class)
public class AgentServiceTests {

		@InjectMocks
		private AgentServiceImplemented agentService;
		
		@Mock
		private AgentRepository agentRepository;
		
		private Agent agent;
		
		private Agent agent1;
		
		@BeforeEach
		public void setup() {
			
			agent = Agent.builder()
						.username("johnsmith")
						.password("js564813")
						.build();
			
			agent1 = Agent.builder()
						.username("oliversim")
						.password("os564659")
						.build();
			
		}
	
		//JUnit test for saveAgentMethod
		@DisplayName("JUnit test for saveAgent method")
		@Test
		public void givenAgentObject_whenSaveAgent_thenReturnAgentObject() {
		
			//given 	
			given(agentRepository.save(agent)).willReturn(agent);
			
			System.out.println(agentRepository);
			System.out.println(agentService);
			
			//when
			Agent savedAgent= agentService.addAgent(agent);
			
			//then
			assertThat(savedAgent).isNotNull();
		}
		
		// JUnit test for getAllAgents method
	    @DisplayName("JUnit test for getAllAgents method")
	    @Test
	    public void givenAgentsList_whenGetAllAgents_thenReturnAgentsList(){
	        // given - precondition or setup
	    	
	        given(agentRepository.findAll()).willReturn(List.of(agent,agent1));

	        // when -  action or the behaviour that we are going test
	        List<Agent> agentList = (List<Agent>) agentService.getAllAgents();

	        // then - verify the output
	        assertThat(agentList).isNotNull();
	        assertThat(agentList.size()).isEqualTo(2);
	    }
	    
		//JUnit test for findAgentByById method
		@DisplayName("JUnit test for findAgentyById method")
		@Test
		public void givenAgentId_whenFindAgentById_thenReturnAgentObject() {
			
			//given
			given(agentRepository.findById(1L)).willReturn(Optional.of(agent));
			
			//when
			Agent savedAgent = ( agentService.findAgentById(agent.getUserId()));
			
			//then
			assertThat(savedAgent).isNotNull();
		}
		
		//JUnit test for updateAgent method
		@DisplayName("JUnit test for updateAgent method")
		@Test
		public void givenAgentObject_whenUpdateAgent_thenReturnUdatedAgent() {
		
			//given
			given(agentRepository.save(agent)).willReturn(agent);
			agent.setUsername("jamiefoy");
			//when
			Agent updatedAgent = agentService.updateAgent(agent);
			
			//then
			assertThat(updatedAgent.getUsername()).isEqualTo("jamiefoy");
		}
		

		@DisplayName("JUnit test for deleteAgentById method")
		@Test
		public void givenAgentId_whenDeleteAgent_thenNothing() {
			
			//given
			BDDMockito.willDoNothing().given(agentRepository).deleteById(1L);
			//when
			agentService.deleteAgentById(1L);
			
			//then
			verify(agentRepository, times(1)).deleteById(1L);
			
		}

	
	
}
