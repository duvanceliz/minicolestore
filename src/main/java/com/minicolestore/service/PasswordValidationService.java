package com.minicolestore.service;

import com.minicolestore.PasswordChange;
import com.minicolestore.User;

public interface PasswordValidationService {
	
	
	public User passwordValidation(PasswordChange passwordChange) throws Exception;

}
