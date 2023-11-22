package com.fdmgroup.crmapi.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import lombok.Builder;


// @NamedQuery(name = "MappedSuperclassCat.findAll",
// 	query = "select c from MappedSuperclassCat c")
@Entity
// @Table(name = "managers")
public class Manager  extends User {

	@OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
	List<Agent> agents;

	public Manager() {}
	
	@Builder
	public Manager(String username, String password, Contact contactDetails) {
		super(username, password, contactDetails);
	}

	@Override
	public String toString() {
		return "Manager: [username=" + getUsername() + ", contact details=" + getContactDetails().toString() + "]";
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
    
}