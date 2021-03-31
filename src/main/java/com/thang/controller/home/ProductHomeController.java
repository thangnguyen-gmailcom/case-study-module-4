package com.thang.controller.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thang.service.IProductService;
import com.thang.service.IProductTypeService;

@Controller
@RequestMapping("/home")
public class ProductHomeController {
	
//	private String getPrincipal(){
//		String userName = null;
//		Object prinObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		if(prinObject instanceof UserDetails) {
//			userName = ((UserDetails)prinObject).getUsername();
//		}else {
//			userName = prinObject.toString();
//		}
//		return userName;
//	}

	@Autowired
	private IProductService productService;
	
	@Autowired
	private IProductTypeService productTypeService;
	
	@GetMapping("/")
	public String homePage(Model model, Pageable pageable) {
//		model.addAttribute("user", getPrincipal());
		model.addAttribute("top3Product", productService.top3Product());
		model.addAttribute("top3ProductRandom", productService.top3ProductRandom());
		model.addAttribute("listProduct", productService.findAll(pageable));
		model.addAttribute("listProductType", productTypeService.findAllProductType());
		return "shop-home";
	}
	
	@GetMapping("/productDetails/{id}")
	public String productDetail(@PathVariable int id, Model model,HttpServletRequest request) {
//		model.addAttribute("user", getPrincipal());
		model.addAttribute("product", productService.findById(id).orElseThrow());
		return "productDetails";
	}
	
	@GetMapping("/product-by-product-type/{id}")
	public String productByProductType(@PathVariable int id,@PageableDefault(size = 8)@SortDefault(sort ="dayAddProduct",direction = Direction.DESC)Pageable pageable,Model model) {
//		model.addAttribute("user", getPrincipal());
		model.addAttribute("top3Product", productService.top3Product());
		model.addAttribute("top3ProductRandom", productService.top3ProductRandom());
		model.addAttribute("listProduct", productService.findAllByProductTypeId(id, pageable));
		model.addAttribute("listProductType", productTypeService.findAllProductType());
		model.addAttribute("ids", id);
		return "shop-home";
	}
	
	
}
