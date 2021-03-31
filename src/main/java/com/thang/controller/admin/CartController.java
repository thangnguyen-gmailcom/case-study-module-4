package com.thang.controller.admin;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thang.model.Bill;
import com.thang.model.BillDetails;
import com.thang.model.Product;
import com.thang.model.User;
import com.thang.service.ICartService;
import com.thang.service.IProductService;
import com.thang.service.IUserService;

@Controller
public class CartController {
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICartService cartService;
	
	@GetMapping("/")
	public String home() {
		return "redirect:/home/";
	}
	
	@GetMapping("/home/shoping-cart")
	public String shopingCart() {
		return "shoping-cart";
	}
	
	@GetMapping("/home/productDetails/addCart/{id}/{quantity}")
	public String addCart(@PathVariable int id, 
			@PathVariable int quantity,
			HttpSession session, 
			HttpServletRequest request) {
		Product product = productService.findById(id).orElseThrow();
		BillDetails billDetails = new BillDetails();
		billDetails.setProduct(product);
		billDetails.setQuantity(quantity);
		double total=0;
		Bill bill = (Bill) session.getAttribute("bill");
		if(bill == null) {
			bill = new Bill();
			bill.setBillDetails(new ArrayList<>());
			bill.getBillDetails().add(billDetails);
			total = billDetails.getProduct().getPrice() * quantity;
			bill.setTotal(total);
		}else {
			boolean isExits = true;
			for(BillDetails billDetail : bill.getBillDetails()) {
				if(billDetail.getProduct().getId() == id) {
					billDetail.setQuantity(quantity + billDetail.getQuantity());
					isExits = false;
				}
				total += billDetail.getQuantity() * billDetail.getProduct().getPrice();
			}
			if(isExits != false) {
				bill.getBillDetails().add(billDetails);
				total += billDetails.getQuantity() * billDetails.getProduct().getPrice();
			}
			bill.setTotal(total);
		}
		session.setAttribute("bill", bill);
		return "redirect:" + request.getHeader("Referer");
	}
	
	@GetMapping("/home/updateCart/{id}/{quantity}")
	public String updateCart(@PathVariable int id, @PathVariable int quantity,HttpSession session, HttpServletRequest request) {
		Bill bill = (Bill) session.getAttribute("bill");
		double total = 0;
		for(BillDetails billDetail : bill.getBillDetails()) {
			if(billDetail.getId() == id) {
				billDetail.setQuantity(quantity);
			}
			total += billDetail.getQuantity() * billDetail.getProduct().getPrice();
		}
		bill.setTotal(total);
		return "redirect:" + request.getHeader("Referer");
	}
	
	@GetMapping("/home/{id}/deleteCart")
	public String deleteCart(@PathVariable int id,HttpSession session, HttpServletRequest request) {
		Bill bill = (Bill) session.getAttribute("bill");
		if(bill.getBillDetails().size() == 1) {
			session.setAttribute("bill", null);
			return "redirect:" + request.getHeader("Referer");
		}
		double total = 0;
		for(BillDetails billDetail : bill.getBillDetails()) {
			if(billDetail.getId() == id) {
				bill.getBillDetails().remove(billDetail);
			}
			total += billDetail.getQuantity() * billDetail.getProduct().getPrice();
		}
		bill.setTotal(total);
		return "redirect:" + request.getHeader("Referer");
	}
	
	@GetMapping("/home/deleteCart")
	public String delete(HttpSession session, HttpServletRequest request) {
		session.setAttribute("bill", null);
		return "redirect:" + request.getHeader("Referer");
	}
	
	@GetMapping("/home/pay")
	public String payForm(HttpSession session,Model model) {
		Bill bill = (Bill) session.getAttribute("bill");
		if(bill == null) {
			return "redirect:/home/";
		}
		return "pay";
	}
	
	@PostMapping("/home/pay")
	public String pay(@RequestParam("deliveryAddress") String deliveryAddress,HttpSession session) {
		Bill bill = (Bill) session.getAttribute("bill");
		User user = userService.findByEmail(getPrincipal());
		bill.setDeliveryAddress(deliveryAddress);
		bill.setUser(user);
		cartService.saveCart(bill);
		session.setAttribute("bill", null);
		return "redirect:/home/";
	}
	
	private String getPrincipal(){
	String userName = null;
	Object prinObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	if(prinObject instanceof UserDetails) {
		userName = ((UserDetails)prinObject).getUsername();
	}else {
		userName = prinObject.toString();
	}
	return userName;
}
}
