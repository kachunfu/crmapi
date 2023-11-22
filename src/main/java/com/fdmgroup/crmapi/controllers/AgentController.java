package com.fdmgroup.crmapi.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.crmapi.exceptions.AgentNotFoundException;
import com.fdmgroup.crmapi.exceptions.InvalidPasswordException;
import com.fdmgroup.crmapi.models.Agent;
import com.fdmgroup.crmapi.services.AgentServiceImplemented;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {
	private AgentServiceImplemented agentService;

	public AgentController(AgentServiceImplemented agentService) {
		this.agentService = agentService;
	}

	@Operation(summary = "Create a new agent")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Agent created successfully", headers = {
			@Header(name = "location", description = "Uri to access to create agent")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping
	public ResponseEntity<?> addAgent(@Valid @RequestBody Agent agent, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(this.agentService.addAgent(agent), HttpStatus.CREATED);
	}

	@Operation(summary = "Find All Agents")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Find all agents successfully", headers = {
			@Header(name = "location", description = "Uri to access to find all agents")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping
	public ResponseEntity<?> getAllAgents() {
		return new ResponseEntity<>(this.agentService.getAllAgents(), HttpStatus.OK);
	}

	@Operation(summary = "Update an Agent")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Agent updated successfully", headers = {
			@Header(name = "location", description = "Uri to access to update agent")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping
	public ResponseEntity<?> updateAgent(@RequestBody Agent agent) {
		return new ResponseEntity<>(this.agentService.updateAgent(agent), HttpStatus.OK);
	}

	@Operation(summary = "Find agent by id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "find an agent successfully", headers = {
			@Header(name = "location", description = "Uri to access to find agent")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/{id}")
	public ResponseEntity<?> findAgentById(@PathVariable Long id) {
		Agent agent = this.agentService.findAgentById(id);
		if (agent == null) {
			return new ResponseEntity<>(
					new AgentNotFoundException(id.toString()).getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agent, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAgentById(@PathVariable Long id) {
		Agent agent = this.agentService.findAgentById(id);
		if (agent == null) {
			return new ResponseEntity<>(
					new AgentNotFoundException(id.toString()).getMessage(),
					HttpStatus.NOT_FOUND);
		}
		this.agentService.deleteAgentById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Login agent by username and password")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Logged In Agent Successfully", headers = {
			@Header(name = "location", description = "Uri to login an agent")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/login/{username}&{password}")
	public ResponseEntity<?> loginAgent(@PathVariable String username, @PathVariable String password) {
		Agent agent = this.agentService.findAgentByUsername(username);
		if (agent == null) {
			return new ResponseEntity<>(
					new AgentNotFoundException(username).getMessage(),
					HttpStatus.NOT_FOUND);
		} else {
			if (agent.getPassword().compareTo(password) == 0) {
				return new ResponseEntity<>(agent, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
					new InvalidPasswordException().getMessage(),
					HttpStatus.FORBIDDEN);
			}
		}
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/manager/{id}")
	public ResponseEntity<?> findAgentByManagerId(@PathVariable Long id) {
		return new ResponseEntity<>(this.agentService.getAllAgentsByManagerId(id), HttpStatus.OK);
	}
}