package com.fdmgroup.crmapi.models;


import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Offer {
	
	@Id
	@Column(name="offer_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_gen")
	private Long id;
	
	@ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "buyer_id")
	private Buyer buyer;
	
	@ManyToOne(cascade = CascadeType.MERGE )
    @JoinColumn(name = "property_id")
	private Property property;
	
	@DecimalMin(value = "0.1", inclusive = false, message = "Price must be greater than 0.1.")
	@Column(nullable = false)
	private BigDecimal offerPrice;
	
	private String status;
	
}

