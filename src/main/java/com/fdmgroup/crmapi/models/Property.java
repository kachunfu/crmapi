package com.fdmgroup.crmapi.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Property {
	
	@Id
	@Column(name="property_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_gen")
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="properties_addresses", joinColumns=@JoinColumn(name="property_id"), inverseJoinColumns=@JoinColumn(name="address_id"))
	private Address address;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name="properties_agents", joinColumns=@JoinColumn(name="property_id"), inverseJoinColumns=@JoinColumn(name="agent_id"))
	private Agent agent;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinTable(name="properties_sellers", joinColumns=@JoinColumn(name="property_id"), inverseJoinColumns=@JoinColumn(name="seller_id"))
	private Seller seller;
	
	@JsonIgnore
	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
	private List<Offer>offers;
	
	private BigDecimal askingPrice;
	
	private String status;

	private LocalDate dateOfListing;
	
	
	
	
}
