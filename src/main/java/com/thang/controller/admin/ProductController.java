package com.thang.controller.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thang.model.Product;
import com.thang.model.ProductType;
import com.thang.service.IProductService;
import com.thang.service.IProductTypeService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private IProductService productService;

	@Autowired
	private IProductTypeService productType;

	@GetMapping("/list")
	public String listProduct(Model model) {
		model.addAttribute("listProduct", productService.findAllProduct());
		return "list-product";
	}

	@GetMapping("/create")
	public String createProductForm(Model model) {
		Product product = new Product();
		product.setProductType(new ProductType());
		model.addAttribute("product", product);
		model.addAttribute("listProductType", productType.findAllProductType());
		return "create-product";
	}

	@PostMapping(value = "/create")
	public String createProductTypeForm(@Validated @ModelAttribute("product") Product product, BindingResult result,
			RedirectAttributes redirect, HttpServletRequest request,Model model) throws Exception {
		if (result.hasFieldErrors()) {
			model.addAttribute("listProductType", productType.findAllProductType());
			return "create-product";
		} else {

			String realPathtoUploads = request.getServletContext().getRealPath("upload");
			File uploadRootDir = new File(realPathtoUploads);
			String uploadLocalPath = "D:\\case-study-module-4\\website-ban-hang\\src\\main\\webapp\\upload";
			File uploadLocalDir = new File(uploadLocalPath);
			if (!uploadRootDir.exists()) {
				uploadRootDir.mkdirs();
			}
			CommonsMultipartFile[] files = product.getImageMutil();
//		        Map<File, String> uploadFile = new HashMap<>();
			for (CommonsMultipartFile commonsMultipartFile : files) {
				// Tên file gốc tại Clien
				String name = commonsMultipartFile.getOriginalFilename();
				if (name != null && name.length() > 0) {
					// Tạo file tại Server
					File severFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

					// Luồng ghi dữ liệu vào file trên Server
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(severFile));
					stream.write(commonsMultipartFile.getBytes());
					stream.close();

					File localFile = new File(uploadLocalDir.getAbsolutePath() + File.separator + name);

					// Luồng ghi dữ liệu vào file trên Server
					BufferedOutputStream streamLocal = new BufferedOutputStream(new FileOutputStream(localFile));
					streamLocal.write(commonsMultipartFile.getBytes());
					streamLocal.close();

					product.setImage(name);

					redirect.addFlashAttribute("mess", "create success");
					productService.saveProduct(product);
				}
			}
		}
		return "redirect:/product/list";

	}

	@GetMapping("/{id}/edit")
	public String editProductTypeForm(@PathVariable int id, Model model, RedirectAttributes redirect,
			HttpServletRequest request) {
		Product product = productService.findById(id).orElseThrow();
		if (product.isDelete() == true) {
			redirect.addFlashAttribute("messError", "id not found !");
			return "redirect:/product/list";
		}
		model.addAttribute("product", product);
		model.addAttribute("listProductType", productType.findAllProductType());
		return "edit-product";
	}

	@PostMapping("/edit")
	public String editProductType(@Validated @ModelAttribute("product") Product product, BindingResult result,
			RedirectAttributes redirect, HttpServletRequest request, Model model) throws Exception{
		if (result.hasFieldErrors()) {
			model.addAttribute("listProductType", productType.findAllProductType());
			return "edit-product";
		} else {
			String realPathtoUploads = request.getServletContext().getRealPath("upload");
			File uploadRootDir = new File(realPathtoUploads);
			String uploadLocalPath = "D:\\case-study-module-4\\website-ban-hang\\src\\main\\webapp\\upload";
			File uploadLocalDir = new File(uploadLocalPath);

			if (!uploadLocalDir.exists()) {
				uploadLocalDir.mkdir();
			}

			CommonsMultipartFile[] files = product.getImageMutil();

			for (CommonsMultipartFile commonsMultipartFile : files) {
//	          lấy tên file gốc phía client
				String name = commonsMultipartFile.getOriginalFilename();
				if (name != null && name.length() > 0) {
					File severFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
//	          Luồng ghi của dữ liệu vào sever
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(severFile));
					stream.write(commonsMultipartFile.getBytes());
					stream.close();

//	           file tại local
					File localFile = new File(uploadLocalDir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream localStream = new BufferedOutputStream(new FileOutputStream(localFile));
					localStream.write(commonsMultipartFile.getBytes());
					localStream.close();

					product.setImage(name);
					redirect.addFlashAttribute("mess", "update success");
					productService.saveProduct(product);
					
				}
			}
		}
		return "redirect:/product/list";
	}

	@GetMapping("/{id}/delete")
	public String deleteProductType(@PathVariable int id, RedirectAttributes redirect) {
		productService.deleteProduct(id);
		redirect.addFlashAttribute("mess", "delete success");
		return "redirect:/product/list";
	}
}
