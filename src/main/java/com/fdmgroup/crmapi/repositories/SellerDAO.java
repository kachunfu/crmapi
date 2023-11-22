package com.fdmgroup.crmapi.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.fdmgroup.crmapi.models.Seller;

@Repository
public interface SellerDAO extends JpaRepository<Seller, Long>{

}