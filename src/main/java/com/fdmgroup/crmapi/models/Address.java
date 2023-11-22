package com.fdmgroup.crmapi.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_gen")
	private Long id;
	
	private String addressLineOne;

	private String city;
	
	private String county;
	
	private String postcode;
	
	private String country;
	
//	public Address() {
//	}
//
//	public Address(String addressLineOne, String city, String county, String postcode, String country) {
//		this.addressLineOne = addressLineOne;
//		this.city = city;
//		this.county = county;
//		this.postcode = postcode;
//		this.country = country;
//	}

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getId() {
		return id;
	}



}
