package com.thang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.thang.model.ProductType;

public interface IProductTypeRepository extends JpaRepository<ProductType, Integer> {

	@Transactional
	@Modifying
	@Procedure(procedureName = "sp_delete_productType")
	void updateProductType(@Param(value = "idDelete") int id);
	
	@Transactional
	@Modifying
	@Query("SELECT p FROM ProductType p WHERE p.isDelete = false")
	List<ProductType> findAllProductType();
	
	

}
