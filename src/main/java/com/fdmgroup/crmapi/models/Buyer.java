package com.fdmgroup.crmapi.models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;

@Entity
public class Buyer extends Customer{
    private BigDecimal budget;
    
    @JsonIgnore
    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    private List<Offer> offers;

    public Buyer() {}
    
    @Builder
    public Buyer(Contact contactDetails, BigDecimal budget) {
        super(contactDetails);
        this.budget = budget;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

}