package com.fdmgroup.crmapi.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import lombok.Builder;


@Entity(name="sellers")
public class Seller extends Customer{
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="sellers_properties", joinColumns=@JoinColumn(name="seller_id"), inverseJoinColumns=@JoinColumn(name="property_id"))
    private List<Property> properties;


    public Seller() {}
    
    @Builder
    public Seller(Contact contactDetails, List<Property> properties) {
        super(contactDetails);
        this.properties = properties;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties){
        this.properties = properties;
    }

}