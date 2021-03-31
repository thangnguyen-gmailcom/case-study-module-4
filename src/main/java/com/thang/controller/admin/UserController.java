package com.thang.controller.admin;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thang.model.User;
import com.thang.service.IRoleService;
import com.thang.service.IUserService;
import com.thang.validator.UserValidation;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;

	@Autowired
	private IRoleService roleService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/signup")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/signup")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, RedirectAttributes redirect, Model model) {
		new UserValidation().validate(user, result);
		if (result.hasFieldErrors()) {
			return "registration";
		} else {
			if (userService.findByEmail(user.getEmail()) == null) {
				userService.registerNewUserAccount(user);
				return "login";
			} else {
				redirect.addFlashAttribute("mess", "email da ton tai ");
				return "redirect:/signup";
			}
		}
	}

	@GetMapping("/user/list")
	public String showUserList(Model model) {
		model.addAttribute("listUser", userService.findAll());
		return "list-user";
	}

	@GetMapping("/user/{id}/edit")
	public String showEditRole(@PathVariable int id, Model model, RedirectAttributes redirect) {
		Optional<User> user = userService.findById(id);
		if (user == null || !user.isPresent()) {
			model.addAttribute("listRole", roleService.findAll());
			redirect.addFlashAttribute("messError", "user not found !");
			return "redirect:/user/list";
		}
		model.addAttribute("listRole", roleService.findAll());
		model.addAttribute("user", user.get());
		return "edit-user";
	}

	@PostMapping("/user/edit")
	public String editUser(@ModelAttribute User user, RedirectAttributes redirect) {
		userService.saveUser(user);
		redirect.addFlashAttribute("mess", "update role success");
		return "redirect:/user/list";
	}
}
