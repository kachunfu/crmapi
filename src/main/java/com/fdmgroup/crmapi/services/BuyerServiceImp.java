package com.fdmgroup.crmapi.services;



import org.springframework.stereotype.Service;

import com.fdmgroup.crmapi.models.Buyer;
import com.fdmgroup.crmapi.repositories.BuyerDAO;

@Service
public class BuyerServiceImp implements BuyerService {

    private BuyerDAO buyerDAO;

    public BuyerServiceImp(BuyerDAO buyerDAO){
        this.buyerDAO = buyerDAO;
    }

    @Override
    public Buyer createBuyer(Buyer buyer){
        return this.buyerDAO.save(buyer);
    }

    @Override
    public void deleteBuyerById(Long id){
        this.buyerDAO.deleteById(id);
    }

    @Override
	public Buyer updateBuyer(Buyer buyer) {
		return this.buyerDAO.save(buyer);
	}

    @Override
    public Buyer findBuyerById(Long id){
        return this.buyerDAO.findById(id).orElse(null);
    }

    @Override
    public Iterable<Buyer> getAllBuyers(){
        return this.buyerDAO.findAll();
    }

    @Override
    public String displayBuyerDetails(Long id){
        return buyerDAO.toString();
    }
}