package com.fdmgroup.crmapi.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;

@Entity
// @Table(name = "agents")
public class Agent  extends User {

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_MANAGER_ID")
	private Manager manager;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="agents_properties", joinColumns=@JoinColumn(name="agent_id"), inverseJoinColumns=@JoinColumn(name="property_id"))
	private List<Property> properties;


	public Agent() {}
	
	@Builder
	public Agent(String username, String password, Contact contactDetails, Manager manager) {
		super(username, password, contactDetails);
		this.manager = manager;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
    
	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	@Override
	public String toString() {
		return "Agent: [username=" + getUsername() + ", contact details=" + getContactDetails().toString() + "]";
	}


}
