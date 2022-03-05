package com.minicolestore.service.Impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minicolestore.Authority;
import com.minicolestore.User;
import com.minicolestore.repository.AuthorityRepository;
import com.minicolestore.repository.UserRepository;
import com.minicolestore.service.CambiarRoleService;

@Service
public class CambiarRoleServiceImpl implements CambiarRoleService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuthorityRepository authorityRepository;

	@Override
	public boolean isAdmin(User user) {

		for (Authority a : user.getAuthority()) {

			if (a.getId() == 1) {

				return true;

			}

		}

		return false;
	}

	@Override
	public void cambiarRol(long id) {

		User user = userRepository.findById(id).get();

		user.setConfpassword("1234");

		Set<Authority> role = new HashSet<Authority>();
		List<Authority> roleList = new ArrayList<Authority>();

		for (Authority authority : authorityRepository.findAll()) {
			roleList.add(authority);

		}

		if (isAdmin(user)) {

			role.add(roleList.get(1));

		} else {
			role.add(roleList.get(0));
		}

		user.setAuthority(role);

		userRepository.save(user);

	}

}
