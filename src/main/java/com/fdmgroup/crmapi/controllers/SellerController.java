
package com.fdmgroup.crmapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import com.fdmgroup.crmapi.services.SellerServiceImp;

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
import com.fdmgroup.crmapi.models.Seller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/sellers")
public class SellerController{

    
    private SellerServiceImp sellerService;

    
    public SellerController(SellerServiceImp sellerService){
        this.sellerService = sellerService;
    }

    @Operation(summary = "Create a new seller")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "successfully created seller", headers = {
			@Header(name = "location", description = "Uri to access to create seller")}, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping
	public ResponseEntity<?> createSeller(@Valid @RequestBody Seller seller, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			Map<String, String> errors = new HashMap<>();

			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(this.sellerService.createSeller(seller), HttpStatus.CREATED);
	}

	@Operation(summary = "Deleting a seller")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "deleted a seller", headers = {
			@Header(name = "location", description = "Uri to access delete a seller")}, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
					@CrossOrigin(origins = "http://localhost:3000")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSellerById(@PathVariable Long id){
		// Seller seller = this.sellerService.findSellerById(id);
		this.sellerService.deleteSellerById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Updating a seller")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "updated a seller", headers = {
			@Header(name = "location", description = "Uri to access update a seller")}, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
	@CrossOrigin(origins = "http://localhost:3000")
	@PutMapping
	public ResponseEntity<?> updateSeller(@RequestBody Seller seller){
		return new ResponseEntity<>(this.sellerService.updateSeller(seller), HttpStatus.OK);
	}

	@Operation(summary = "Find an seller by id")
	@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "find seller", headers = {
			@Header(name = "location", description = "Uri to access to find seller")}, content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping("/{id}")
	public ResponseEntity<?> findSellerById(@PathVariable Long id){
		Seller seller = this.sellerService.findSellerById(id);
		
		// if(seller == null) {
		// 	return new ResponseEntity<>(
		// 			new SellerNotFoundException("Seller does not exist").getMessage(),
		// 			HttpStatus.NOT_FOUND);
		// }
		return new ResponseEntity<>(seller, HttpStatus.OK);
	}

	@Operation(summary = "Find All Sellers")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Find all sellers", headers = {
			@Header(name = "location", description = "Uri to access to find all sellers")
		}, content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
		})
	})
	@CrossOrigin(origins = "http://localhost:3000")
	@GetMapping
	public ResponseEntity<?> getAllSellers() {
		return new ResponseEntity<>(this.sellerService.getAllSellers(), HttpStatus.OK);
	}




}
