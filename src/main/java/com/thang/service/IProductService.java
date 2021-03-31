package com.thang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.thang.model.Product;
import com.thang.model.ProductType;

public interface IProductService {
	
	Optional<Product> findById(int id);

	void saveProduct(Product product);

	void deleteProduct(int id);

	List<Product> findAllProduct();
	
	List<Product> findByProductType(ProductType productType);
	
	List<Product> top3Product();
	
	List<Product> top3ProductRandom();
	
	Page<Product> findAllByProductTypeId(int id, Pageable pageable);
	
	Page<Product> findAll(Pageable pageable);
}
