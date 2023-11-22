package com.fdmgroup.crmapi.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.fdmgroup.crmapi.models.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Long>{

}