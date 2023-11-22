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

import com.fdmgroup.crmapi.exceptions.ManagerNotFoundException;
import com.fdmgroup.crmapi.exceptions.InvalidPasswordException;
import com.fdmgroup.crmapi.models.Manager;
import com.fdmgroup.crmapi.services.ManagerServiceImplemented;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/managers")
public class ManagerController {
	private ManagerServiceImplemented managerService;

	public ManagerController(ManagerServiceImplemented managerService) {
		this.managerService = managerService;
	}

	@Operation(summary = "Create a new manager")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "201", description = "Manager created successfully", headers = {
			@Header(name = "location", description = "Uri to access to create manager")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping
	public ResponseEntity<?> addManager(@Valid @RequestBody Manager manager, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(this.managerService.addManager(manager), HttpStatus.CREATED);
	}

	@Operation(summary = "Find All Managers")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Find all managers successfully", headers = {
			@Header(name = "location", description = "Uri to access to find all managers")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping
	public ResponseEntity<?> getAllManagers() {
		return new ResponseEntity<>(this.managerService.getAllManagers(), HttpStatus.OK);
	}

	@Operation(summary = "Update an Manager")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Manager updated successfully", headers = {
			@Header(name = "location", description = "Uri to access to update manager")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping
	public ResponseEntity<?> updateManager(@RequestBody Manager manager) {
		return new ResponseEntity<>(this.managerService.updateManager(manager), HttpStatus.OK);
	}

	@Operation(summary = "Find manager by id")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "find an manager successfully", headers = {
			@Header(name = "location", description = "Uri to access to find manager")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/{id}")
	public ResponseEntity<?> findManagerById(@PathVariable Long id) {
		Manager manager = this.managerService.findManagerById(id);
		if (manager == null) {
			return new ResponseEntity<>(
					new ManagerNotFoundException(id.toString()).getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(manager, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteManagerById(@PathVariable Long id) {
		Manager manager = this.managerService.findManagerById(id);
		if (manager == null) {
			return new ResponseEntity<>(
					new ManagerNotFoundException(id.toString()).getMessage(),
					HttpStatus.NOT_FOUND);
		}
		this.managerService.deleteManagerById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Login manager by username and password")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Logged In Manager Successfully", headers = {
			@Header(name = "location", description = "Uri to login an manager")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/login/{username}&{password}")
	public ResponseEntity<?> loginManager(@PathVariable String username, @PathVariable String password) {
		Manager manager = this.managerService.findManagerByUsername(username);
		if (manager == null) {
			return new ResponseEntity<>(
					new ManagerNotFoundException(username).getMessage(),
					HttpStatus.NOT_FOUND);
		} else {
			if (manager.getPassword().compareTo(password) == 0) {
				return new ResponseEntity<>(manager, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(
					new InvalidPasswordException().getMessage(),
					HttpStatus.FORBIDDEN);
			}
		}
	}
}