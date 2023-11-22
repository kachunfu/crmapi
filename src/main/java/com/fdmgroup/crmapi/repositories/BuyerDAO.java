package com.fdmgroup.crmapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.fdmgroup.crmapi.models.Buyer;

@Repository
public interface BuyerDAO extends JpaRepository<Buyer, Long>{

}