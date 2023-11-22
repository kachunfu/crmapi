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
import com.fdmgroup.crmapi.models.Offer;
import com.fdmgroup.crmapi.services.OfferService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

	private OfferService offerService;

	public OfferController(OfferService offerService) {
		this.offerService = offerService;
	}
	
	@Operation(summary = "Create a new offer")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Offer created successfully", headers = {
			@Header(name = "location", description = "Uri to access to create offer") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping
	public ResponseEntity<?> createOffer(@Valid @RequestBody Offer offer, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			
			for(FieldError error: bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(this.offerService.saveOffer(offer), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Find All offer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Find all offers successfully", headers = {
			@Header(name = "location", description = "Uri to access to find all offers") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping
	public ResponseEntity<?> findAllOffers() {
		return new ResponseEntity<>(this.offerService.findAllOffers(), HttpStatus.OK);
	}
	
	@Operation(summary = "Find offer by id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "find an offer successfully", headers = {
			@Header(name = "location", description = "Uri to access to find offer") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/{id}")
	public ResponseEntity<?> findOfferById(@PathVariable Long id) {
		Offer offer = this.offerService.findOfferById(id);

		if (offer == null) {
			return new ResponseEntity<>(
					new OfferNotFoundException("The offer with id: " + id + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(offer, HttpStatus.OK);
	}
	
	@Operation(summary = "Update an offer")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Offer updated successfully", headers = {
			@Header(name = "location", description = "Uri to access to update offer") }, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping
	public ResponseEntity<?> updateOffer(@RequestBody Offer offer) {
		return new ResponseEntity<>(this.offerService.updateOffer(offer), HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAddressById(@PathVariable Long id) {
		Offer offer = this.offerService.findOfferById(id);

		if (offer == null) {
			return new ResponseEntity<>(
					new OfferNotFoundException("The offer with id: " + id + " not found").getMessage(),
					HttpStatus.NOT_FOUND);
		}

		this.offerService.deleteOfferById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/property/{id}")
	public ResponseEntity<?> findOffersByPropertyId(@PathVariable Long id) {
		return new ResponseEntity<>(this.offerService.getAllOffersByPropertyId(id), HttpStatus.OK);
	}
	
}
