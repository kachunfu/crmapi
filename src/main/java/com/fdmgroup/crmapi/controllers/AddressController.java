package com.fdmgroup.crmapi.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.crmapi.exceptions.AddressNotFoundException;
import com.fdmgroup.crmapi.models.Address;
import com.fdmgroup.crmapi.services.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
	
	private AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@Operation(summary = "Create a new address")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Address created successfully", headers = {
			@Header(name = "location", description = "Address to access to create address") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@PostMapping
	public ResponseEntity<?> createAddress(@Valid @RequestBody Address address, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			
			for(FieldError error: bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(this.addressService.saveAddress(address), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Find address by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "find an address successfully", headers = {
			@Header(name = "location", description = "Uri to access to find address") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping("/{id}")
	public ResponseEntity<?> findAddressById(@PathVariable Long id) {
		Address address = this.addressService.findAddressById(id);

		if (address == null) {
			return new ResponseEntity<>(
					new AddressNotFoundException("The address with id: " + id + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(address, HttpStatus.OK);
	}
	
	@Operation(summary = "Update an address")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Address updated successfully", headers = {
			@Header(name = "location", description = "Uri to access to update address") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@PutMapping
	public ResponseEntity<?> updateAddress(@RequestBody Address address) {
		return new ResponseEntity<>(this.addressService.updateAddress(address), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {
		Address address = this.addressService.findAddressById(id);

		if (address == null) {
			return new ResponseEntity<>(
					new AddressNotFoundException("The address with id: " + id + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}

		this.addressService.deleteAddressById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
