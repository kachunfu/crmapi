package com.fdmgroup.crmapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.crmapi.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
