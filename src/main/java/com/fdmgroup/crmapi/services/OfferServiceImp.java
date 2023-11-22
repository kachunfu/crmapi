package com.fdmgroup.crmapi.services;

import org.springframework.stereotype.Service;

import com.fdmgroup.crmapi.models.Offer;
import com.fdmgroup.crmapi.repositories.OfferRepository;

@Service
public class OfferServiceImp implements OfferService {

	private OfferRepository offerRepository;
	
	public OfferServiceImp(OfferRepository offerRepository) {
		this.offerRepository = offerRepository;
	}
	
	@Override
	public Offer saveOffer(Offer offer) {
		return this.offerRepository.save(offer);
	}

	@Override
	public Offer updateOffer(Offer offer) {
		return this.offerRepository.save(offer);
	}
	
	@Override
	public void deleteOfferById(Long id) {
		this.offerRepository.deleteById(id);
	}

	@Override
	public Offer findOfferById(Long id) {
		return this.offerRepository.findById(id).orElse(null);
	}

	@Override
	public Iterable<Offer> findAllOffers() {
		return this.offerRepository.findAll();
	}

	@Override
	public Iterable<Offer> getAllOffersByPropertyId(Long id) {
		return this.offerRepository.findByPropertyId(id).orElse(null);
	}

}
