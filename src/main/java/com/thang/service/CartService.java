package com.thang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thang.model.Bill;
import com.thang.repository.IBillRepository;

@Service
public class CartService implements ICartService{

	@Autowired
	private IBillRepository billRepository;
	
	@Override
	public void saveCart(Bill bill) {
		billRepository.save(bill);
	}
	
	
}
