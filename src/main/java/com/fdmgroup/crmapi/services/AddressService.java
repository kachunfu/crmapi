package com.fdmgroup.crmapi.services;



import com.fdmgroup.crmapi.models.Address;


public interface AddressService {

	Address saveAddress(Address address);
	
	Address updateAddress(Address address);
	
	void deleteAddressById(Long id);

	Address findAddressById(Long id);
}
