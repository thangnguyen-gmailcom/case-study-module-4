package com.thang.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.thang.model.Product;
import com.thang.model.ProductType;

public interface IProductPerository extends JpaRepository<Product, Integer> {
	
	@Transactional
	@Modifying
	@Query("SELECT p FROM Product p WHERE p.isDelete = false ORDER BY p.dayAddProduct DESC")
	List<Product> findAllProduct();
	
	@Transactional
	@Modifying
	@Query("UPDATE Product p SET p.isDelete = true WHERE p.id = :id")
	void updateProduct(@Param(value = "id") int id);
	
	@Transactional
	@Modifying
	@Query("SELECT p FROM Product p WHERE p.productType = :idProductType AND p.isDelete = false ORDER BY p.dayAddProduct DESC")
	List<Product> findByProductType(@Param(value="idProductType") ProductType id);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value="SELECT * FROM Product p WHERE p.isDelete = false ORDER BY p.dayAddProduct DESC LIMIT 3")
	List<Product> top3Product();
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true ,value="SELECT * FROM Product p WHERE p.isDelete = false ORDER BY RAND() LIMIT 3")
	List<Product> randomTop3Product();
	
	Page<Product> findAllByProductTypeId(@Param(value = "id") int id, Pageable pageable);
	
	Page<Product> findAll(Pageable pageable);
	
}
