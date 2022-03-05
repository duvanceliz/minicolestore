package com.minicolestore.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minicolestore.Authority;
import com.minicolestore.User;
import com.minicolestore.repository.UserRepository;
import com.minicolestore.service.MapAuthService;

@Service
public class MapAuthServiceImpl implements MapAuthService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> getUser() {

		List<User> roleList = new ArrayList<User>();

		for (User u : userRepository.findAll()) {

			for (Authority a : u.getAuthority()) {

				if (a.getId() == 1) {

					u.setAdmin(true);

				} else {

					u.setAdmin(false);

				}

			}

			roleList.add(u);

		}

		return roleList;
	}

}
