
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
import com.fdmgroup.crmapi.services.BuyerServiceImp;
import com.fdmgroup.crmapi.models.Buyer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/buyers")
public class BuyerController{

    private BuyerServiceImp buyerService;

    
    public BuyerController(BuyerServiceImp buyerService){
        this.buyerService = buyerService;
    }

    @Operation(summary = "Create a new buyer")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "successfully created buyer", headers = {
			@Header(name = "location", description = "Uri to access to create buyer")}, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
	
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping
	public ResponseEntity<?> createBuyer(@Valid @RequestBody Buyer buyer, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(this.buyerService.createBuyer(buyer), HttpStatus.CREATED);
	}

	@Operation(summary = "Deleting a buyer")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "deleted a buyer", headers = {
			@Header(name = "location", description = "Uri to access delete a buyer")}, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
	@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBuyerById(@PathVariable Long id){
		// Buyer buyer = this.buyerService.findBuyerById(id);
		this.buyerService.deleteBuyerById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Updating a buyer")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "updated a buyer", headers = {
			@Header(name = "location", description = "Uri to access update a buyer")}, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping
	public ResponseEntity<?> updateBuyer(@RequestBody Buyer buyer){
		return new ResponseEntity<>(this.buyerService.updateBuyer(buyer), HttpStatus.OK);
	}

	@Operation(summary = "Find an buyer by id")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find buyer", headers = {
			@Header(name = "location", description = "Uri to access to find buyer")}, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/{id}")
	public ResponseEntity<?> findBuyerById(@PathVariable Long id){
		Buyer buyer = this.buyerService.findBuyerById(id);
		
		// if(seller == null) {
		// 	return new ResponseEntity<>(
		// 			new SellerNotFoundException("Seller does not exist").getMessage(),
		// 			HttpStatus.NOT_FOUND);
		// }
		return new ResponseEntity<>(buyer, HttpStatus.OK);
	}

	@Operation(summary = "Find All buyers")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Find all buyers", headers = {
			@Header(name = "location", description = "Uri to access to find all buyers")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping
	public ResponseEntity<?> getAllBuyers() {
		return new ResponseEntity<>(this.buyerService.getAllBuyers(), HttpStatus.OK);
	}


}
