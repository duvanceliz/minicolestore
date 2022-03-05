package com.minicolestore.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.minicolestore.PasswordChange;
import com.minicolestore.User;
import com.minicolestore.repository.UserRepository;
import com.minicolestore.service.PasswordValidationService;

import net.bytebuddy.utility.RandomString;

@Service
public class PasswordValidationServiceImpl implements PasswordValidationService {

	@Autowired
	UserRepository userRepo;

	private boolean validation(PasswordChange passwordChange) throws Exception {
		if (!passwordChange.getPassword().equals(passwordChange.getPasswordConf())) {

			throw new Exception("Las contraseñas no coinciden");

		}

		return true;
	}

	@Override
	public User passwordValidation(PasswordChange passwordChange) throws Exception {

		User user = userRepo.findById(passwordChange.getId()).get();

		if (validation(passwordChange)) {
			// token change
			String radomCode = RandomString.make(64);

			user.setCodigoVerificacion(radomCode);

			// set new password
			
			String password = passwordChange.getPassword();

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

			user.setPassword(bCryptPasswordEncoder.encode(password));

			
			
			// save
			
			user = userRepo.save(user);

		}

		return user;
	}

}
