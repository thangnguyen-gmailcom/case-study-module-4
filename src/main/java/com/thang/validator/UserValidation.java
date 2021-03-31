package com.thang.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.thang.model.User;

public class UserValidation implements org.springframework.validation.Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		String phoneNumber = user.getPhoneNumber();
		ValidationUtils.rejectIfEmpty(errors,"phoneNumber", "phoneNumber.empty");
		if(phoneNumber.length() > 11 || phoneNumber.length()<10) {
			errors.rejectValue("phoneNumber", "phoneNumber.length");
		}
		if(!phoneNumber.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b")) {
			errors.rejectValue("phoneNumber", "phoneNumber.matches");
		}
		
		String email = user.getEmail();
		if(email.length() < 10 || email.length() > 50 ) {
			errors.rejectValue("email", "email.lenght");
		}
		if(!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			errors.rejectValue("email", "email.matches");
		}
		String password = user.getPassword();
		if(password.length()<8 || password.length() > 16) {
			errors.rejectValue("password", "password.lenght");
		}
	}
}
