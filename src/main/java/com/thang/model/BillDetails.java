package com.thang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class BillDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "quantity")
	private int quantity;
	
	@ManyToOne
	private Bill bill;
	
	@ManyToOne
	private Product product;

	@Override
	public String toString() {
		return "BillDetails [id=" + id + ", quantity=" + quantity + "]";
	}
	
	
}
