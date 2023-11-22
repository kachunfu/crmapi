package com.fdmgroup.crmapi.services;

import com.fdmgroup.crmapi.models.Property;

public interface PropertyService {

	Property saveProperty(Property property);
	
	Property updateProperty(Property property);
	
	void deletePropertyById(Long id);

	Property findPropertyById(Long id);

	Iterable<Property>findAllProperties();
	
}
