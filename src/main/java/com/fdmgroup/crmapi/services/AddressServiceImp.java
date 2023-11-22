package com.fdmgroup.crmapi.services;

import org.springframework.stereotype.Service;

import com.fdmgroup.crmapi.models.Address;
import com.fdmgroup.crmapi.repositories.AddressRepository;

@Service
public class AddressServiceImp implements AddressService{

private AddressRepository addressRepository;
	
	public AddressServiceImp(AddressRepository addressRepository) {
		this.addressRepository = addressRepository;
	}

	@Override
	public Address saveAddress(Address address) {
		return this.addressRepository.save(address);
	}

	@Override
	public Address updateAddress(Address address) {
		return this.addressRepository.save(address);
	}

	@Override
	public void deleteAddressById(Long id) {
		this.addressRepository.deleteById(id);

	}

	@Override
	public Address findAddressById(Long id) {
		return this.addressRepository.findById(id).orElse(null);
	}
	
	
}
