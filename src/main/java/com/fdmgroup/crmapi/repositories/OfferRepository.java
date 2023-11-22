package com.fdmgroup.crmapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fdmgroup.crmapi.models.Offer;



@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>{

    /**
     * @param managerId This is the manager id
     * 
     * Uses a @Query to select the results from the repository that match the managerId param exactly
     */
    @Query("select offer from Offer offer where offer.property.id = ?1")
    public Optional<List<Offer>> findByPropertyId(Long propertyId);
}
