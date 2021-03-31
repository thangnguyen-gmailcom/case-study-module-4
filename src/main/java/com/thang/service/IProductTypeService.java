package com.thang.service;

import java.util.List;
import java.util.Optional;

import com.thang.model.ProductType;

public interface IProductTypeService {
	
	Optional<ProductType> findById(int id);
	
	void saveProductType(ProductType productType);
	
	void deleteProductType(int id);
	
	ProductType checkProductType(ProductType productType);
	
	List<ProductType> findAllProductType();
	

}
