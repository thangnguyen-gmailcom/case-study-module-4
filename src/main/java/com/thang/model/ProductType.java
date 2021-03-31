package com.thang.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@NamedStoredProcedureQuery(name = "ProductType.updateModelEntity", 
procedureName = "sp_delete_productType", parameters = {
  @StoredProcedureParameter(mode = ParameterMode.IN,name = "idDelete", type = Integer.class)})
public class ProductType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "* product type cannot be left blank")
	@Size(min = 10, max = 70 , message = "* product type lengths range from 10 to 70")
	private String productType;
	
	private boolean isDelete = false;
	
	@OneToMany(mappedBy = "productType")
	private List<Product> products;
	
}
