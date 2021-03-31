package com.thang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thang.model.ProductType;
import com.thang.repository.IProductTypeRepository;

@Service
public class ProductTypeService implements IProductTypeService {

	@Autowired
	private IProductTypeRepository productTypeRepository;
	
	@Override
	public List<ProductType> findAllProductType() {
		
		return productTypeRepository.findAllProductType();
	}
	
	@Override
	public Optional<ProductType> findById(int id) {
		return productTypeRepository.findById(id);
	}

	@Override
	public void saveProductType(ProductType productType) {
		productTypeRepository.save(productType);
	}

	@Override
	public void deleteProductType(int id) {
		productTypeRepository.updateProductType(id);;
	}
	
	public ProductType checkProductType(ProductType productType) {
		for(ProductType pr : findAllProductType()) {
			if(productType.getProductType().equals(pr.getProductType())) {
				return productType;
			}
		}
		return null;
	}

}
