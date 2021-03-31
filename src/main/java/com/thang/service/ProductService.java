package com.thang.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thang.model.Product;
import com.thang.model.ProductType;
import com.thang.repository.IProductPerository;

@Service
public class ProductService implements IProductService{
	
	@Autowired
	private IProductPerository productRepository;

	@Override
	public Optional<Product> findById(int id) {
		return productRepository.findById(id);
	}

	@Override
	public void saveProduct(Product product) {
		productRepository.save(product);
	}

	@Override
	public void deleteProduct(int id) {
		productRepository.updateProduct(id);
		
	}

	@Override
	public List<Product> findAllProduct() {
		return productRepository.findAllProduct();
	}

	@Override
	public List<Product> findByProductType(ProductType productType) {
		return productRepository.findByProductType(productType);
	}

	@Override
	public List<Product> top3Product() {
		return productRepository.top3Product();
	}

	@Override
	public List<Product> top3ProductRandom() {
		return productRepository.randomTop3Product();
	}

	@Override
	public Page<Product> findAllByProductTypeId(int id, Pageable pageable) {
		return productRepository.findAllByProductTypeId(id,pageable);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

}
