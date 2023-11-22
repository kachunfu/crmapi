package com.fdmgroup.crmapi.services;

import com.fdmgroup.crmapi.models.Seller;

public interface SellerService {
    public Seller createSeller(Seller seller);

    public void deleteSellerById(Long id);

    public Seller updateSeller(Seller seller);

    public Seller findSellerById(Long id);

    public Iterable<Seller> getAllSellers();

    public String displaySellerDetails(Long id);



}