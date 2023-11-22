package com.fdmgroup.crmapi.services;


import org.springframework.stereotype.Service;

import com.fdmgroup.crmapi.models.Property;
import com.fdmgroup.crmapi.repositories.PropertyRepository;

@Service
public class PropertyServiceImp implements PropertyService {

	private PropertyRepository propertyRepository;
	
	public PropertyServiceImp(PropertyRepository propertyRepository) {
		this.propertyRepository = propertyRepository;
	}
	
	@Override
	public Property saveProperty(Property property) {
		return this.propertyRepository.save(property);
	}

	@Override
	public Property updateProperty(Property property) {
		return this.propertyRepository.save(property);
	}
	
	@Override
	public void deletePropertyById(Long id) {
		this.propertyRepository.deleteById(id);
	}

	@Override
	public Property findPropertyById(Long id) {
		return this.propertyRepository.findById(id).orElse(null);
//		return this.propertyRepository.findById(id);
	}

	@Override
	public Iterable<Property> findAllProperties() {
		return this.propertyRepository.findAll();
	}

}
