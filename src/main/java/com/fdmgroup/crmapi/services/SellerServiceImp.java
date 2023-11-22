package com.fdmgroup.crmapi.services;

import org.springframework.stereotype.Service;

import com.fdmgroup.crmapi.models.Seller;
import com.fdmgroup.crmapi.repositories.SellerDAO;

@Service
public class SellerServiceImp implements SellerService{
    
    private SellerDAO sellerDAO;

    public SellerServiceImp(SellerDAO sellerDAO){
        this.sellerDAO = sellerDAO;
    }

    @Override
    public Seller createSeller(Seller seller){
        return this.sellerDAO.save(seller);
    }

    @Override
    public void deleteSellerById(Long id){
        this.sellerDAO.deleteById(id);
    }

    @Override
	public Seller updateSeller(Seller seller) {
		return this.sellerDAO.save(seller);
		
	}

    @Override
    public Seller findSellerById(Long id) {
        return this.sellerDAO.findById(id).orElse(null);
    }

    @Override
    public Iterable<Seller> getAllSellers(){
        return this.sellerDAO.findAll();
    }

    @Override
    public String displaySellerDetails(Long id){
        return sellerDAO.toString();
    }
    
}
