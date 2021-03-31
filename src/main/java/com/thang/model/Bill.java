package com.thang.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDateTime orderDate = LocalDateTime.now();
	
	private Date deliveryDate;
	
	private String deliveryAddress;
	
	private boolean deliveryStatus;
	
	private double total;
	
	@OneToMany(mappedBy = "bill", cascade = CascadeType.PERSIST)
	private List<BillDetails> billDetails;
	
	@ManyToOne
	private User user;

	@Override
	public String toString() {
		return "Bill [id=" + id + ", orderDate=" + orderDate + ", deliveryDate=" + deliveryDate + ", deliveryAddress="
				+ deliveryAddress + ", deliveryStatus=" + deliveryStatus + "]";
	}
}
