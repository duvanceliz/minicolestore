package com.minicolestore.service.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.minicolestore.Authority;
import com.minicolestore.User;
import com.minicolestore.repository.AuthorityRepository;
import com.minicolestore.repository.UserRepository;
import com.minicolestore.service.UserService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	UserRepository userRepository;
	
	private boolean checkEmailAvailable(User user) throws Exception {
		Optional<User> emailFound = userRepository.findByEmailIgnoreCase(user.getEmail());
	
		if (emailFound.isPresent()) {
			throw new Exception("El correo electronico ya se encuentra registrado!");
		}
		return true;
	}
	
	private boolean checkUsernameAvailable(User user) throws Exception {
		Optional<User> userFound = userRepository.findByUsername(user.getUsername());
	
		if (userFound.isPresent()) {
			throw new Exception("Nombre de usuario no disponible!");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if ( !user.getPassword().equals(user.getConfpassword())) {
			throw new Exception("Las contraseñas no coinciden!");
		}
		return true;
	}

	@Override
	public User createUser(User user) throws Exception {

		if (checkUsernameAvailable(user) && checkPasswordValid(user) && checkEmailAvailable(user)) {
			
			String password = user.getPassword();

			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

			user.setPassword(bCryptPasswordEncoder.encode(password));
			
			user.setEnabled(false);
			user.setFechaCreacion(new Date());
			
			String radomCode = RandomString.make(64);
			
			user.setCodigoVerificacion(radomCode);

			Set<Authority> role = new HashSet<Authority>();
			List<Authority> roleList = new ArrayList<Authority>();

			for (Authority authority : authorityRepository.findAll()) {
				roleList.add(authority);

			}

			role.add(roleList.get(1));

			user.setAuthority(role);
			user = userRepository.save(user);
		}
		return user;
	}

}
