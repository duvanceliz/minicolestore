package com.minicolestore.service.Impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.minicolestore.User;
import com.minicolestore.service.AuthService;
import com.minicolestore.service.UserService;

@Service
public class AuthServiceImpl implements AuthService {

	@Override
	public boolean getAuth(Authentication auth) {
		

		for (GrantedAuthority authority : auth.getAuthorities()) {

			if (authority.getAuthority().contains("ROLE_ADMIN")) {
				

				return true;
				

			}
		}
		
		return false;
	}

}
