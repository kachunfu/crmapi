package com.fdmgroup.crmapi.services;
import com.fdmgroup.crmapi.models.Offer;

public interface OfferService {
	
	Offer saveOffer(Offer offer);
	
	Offer updateOffer(Offer offer);
	
	void deleteOfferById(Long id);

	Offer findOfferById(Long id);
	
	Iterable<Offer>findAllOffers();

	Iterable<Offer> getAllOffersByPropertyId(Long id);
}
