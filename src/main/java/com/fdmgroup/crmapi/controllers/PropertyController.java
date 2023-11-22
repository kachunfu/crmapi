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

import com.fdmgroup.crmapi.exceptions.OfferNotFoundException;
import com.fdmgroup.crmapi.models.Property;
import com.fdmgroup.crmapi.services.PropertyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

	private PropertyService propertyService;

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
	@Operation(summary = "Create a new property")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Property created successfully", headers = {
			@Header(name = "location", description = "Uri to access to create property") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping
	public ResponseEntity<?> createProperty(@Valid @RequestBody Property property, BindingResult bindingResult){
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			
			for(FieldError error: bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(this.propertyService.saveProperty(property), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Find All property")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Find all properties successfully", headers = {
			@Header(name = "location", description = "Uri to access to find all properties") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping
	public ResponseEntity<?> findAllProperties() {
		return new ResponseEntity<>(this.propertyService.findAllProperties(), HttpStatus.OK);
	}
	
	
	
	@Operation(summary = "Find property by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "find an property successfully", headers = {
			@Header(name = "location", description = "Uri to access to find property") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/{id}")
	public ResponseEntity<?> findPropertyById(@PathVariable Long id) {
		Property property = this.propertyService.findPropertyById(id);

		if (property == null) {
			return new ResponseEntity<>(
					new OfferNotFoundException("The property with id: " + id + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(property, HttpStatus.OK);
	}
	
	@Operation(summary = "Update an property")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Property updated successfully", headers = {
			@Header(name = "location", description = "Uri to access to update property") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping
	public ResponseEntity<?> updateOffer(@RequestBody Property property) {
		return new ResponseEntity<>(this.propertyService.updateProperty(property), HttpStatus.OK);
	}
	
	@Operation(summary = "Delete an property")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Property deleted successfully", headers = {
			@Header(name = "location", description = "Uri to access to update property") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePropertyById(@PathVariable Long id) {
		Property property = this.propertyService.findPropertyById(id);

		if (property == null) {
			return new ResponseEntity<>(
					new OfferNotFoundException("The property with id: " + id + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}

		this.propertyService.deletePropertyById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
