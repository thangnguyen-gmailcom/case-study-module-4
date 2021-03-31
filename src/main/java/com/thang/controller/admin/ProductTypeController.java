package com.thang.controller.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thang.model.ProductType;
import com.thang.service.IProductTypeService;

@Controller
@RequestMapping("/product-type")
public class ProductTypeController {

	@Autowired
	private IProductTypeService productService;

	@GetMapping("/list")
	public String showListProductType(Model model) {
		model.addAttribute("listProductType", productService.findAllProductType());
		return "list-product-type";
	}

	@GetMapping(value = "/create", produces = "text/html; charset=utf-8")
	public String createProductTypeForm(Model model) {
		model.addAttribute("productTypes", new ProductType());
		return "create-product-type";
	}

	@PostMapping(value = "/create", produces = "text/html; charset=utf-8")
	public String createProductTypeForm(@Validated @ModelAttribute("productTypes") ProductType productType,
			BindingResult result, RedirectAttributes redirect) {
		if (result.hasFieldErrors()) {
			return "create-product-type";
		} else {
			if (productService.checkProductType(productType) == null) {
				redirect.addFlashAttribute("mess", "create success");
				productService.saveProductType(productType);
				return "redirect:/product-type/list";
			} else {
				redirect.addFlashAttribute("messError", "product type already exist");
				return "redirect:/product-type/list";
			}
		}

	}

	@GetMapping("/{id}/edit")
	public String editProductTypeForm(@PathVariable int id, Model model,RedirectAttributes redirect) {
		Optional<ProductType> productType = productService.findById(id);
		if(!productType.isPresent() || productType.get().isDelete()== true) {
			redirect.addFlashAttribute("messError", "id not found !");
			return "redirect:/product-type/list";
		}
		model.addAttribute("productTypes", productType.get());
		return "edit-product-type";
	}

	@PostMapping("/edit")
	public String editProductType(@Validated @ModelAttribute("productTypes") ProductType productType,
			BindingResult result, RedirectAttributes redirect) {
		if (result.hasFieldErrors()) {
			return "edit-product-type";
		} else {
			if (productService.checkProductType(productType) == null) {
				redirect.addFlashAttribute("mess", "update success");
				productService.saveProductType(productType);
				return "redirect:/product-type/list";
			} else {
				redirect.addFlashAttribute("messError", "product type already exist");
				return "redirect:/product-type/list";
			}
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteProductType(@PathVariable int id, RedirectAttributes redirect) {
		productService.deleteProductType(id);
		redirect.addFlashAttribute("mess", "delete success");
		return "redirect:/product-type/list";
	}
}
