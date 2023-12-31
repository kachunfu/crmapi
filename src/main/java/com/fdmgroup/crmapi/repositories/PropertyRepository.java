package com.fdmgroup.crmapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.crmapi.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>{

}
