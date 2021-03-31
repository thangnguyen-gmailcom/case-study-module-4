package com.thang.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Data;

@Data
@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message ="* The product name cannot be left blank")
	@Length(min = 5, max = 250, message = "* Product names from 5 to 250 characters")
	private String productName;
	
	@Column(columnDefinition = "text")
	@NotEmpty(message = "* The product description cannot be left blank")
	@Length(min = 20, message = "* product description at least 20 characters")
	private String description;
	
	@Min(value =  5, message = "* The product price min 5")
	private double price;
	
	@Min(value = 5)
	@Max(value = 10000)
	private int quantityInStock;
	
	@Transient
	private CommonsMultipartFile[] imageMutil;
	
	@Column(columnDefinition = "text")
	private String image;
	
	private boolean isDelete = false;
	
	private int percentDiscount = 0;
	
	private LocalDateTime dayAddProduct = LocalDateTime.now();
	
	@OneToMany(mappedBy = "product")
	private List<BillDetails> billDetails;
	
	@ManyToOne
	@NotNull(message = "The product type connot be left blank")
	private ProductType productType;
	
}
