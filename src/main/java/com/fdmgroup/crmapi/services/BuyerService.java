package com.fdmgroup.crmapi.services;

import com.fdmgroup.crmapi.models.Buyer;

public interface BuyerService {

    public Buyer createBuyer(Buyer buyer);

    public void deleteBuyerById(Long id);

    public Buyer updateBuyer(Buyer buyer);

    public Buyer findBuyerById(Long id);

    public Iterable<Buyer> getAllBuyers();

    public String displayBuyerDetails(Long id);

}