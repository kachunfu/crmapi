package com.fdmgroup.crmapi.models;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


import javax.persistence.Id;

@MappedSuperclass
public abstract class Customer{
    @Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customergen")
	@SequenceGenerator(name = "customergen", sequenceName = "customerseq", allocationSize = 1)
	private long Id;

	@OneToOne(cascade = {CascadeType.ALL})
	private Contact contactDetails;

	public Customer() {}

	public Customer(Contact contactDetails){
		super();
		this.contactDetails = contactDetails;
	}

	public long getId(){
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public Contact getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(Contact contactDetails) {
		this.contactDetails = contactDetails;
	}

}