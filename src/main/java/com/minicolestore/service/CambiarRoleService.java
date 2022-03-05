package com.minicolestore.service;

import com.minicolestore.User;

public interface CambiarRoleService {
	
	public void cambiarRol(long id);
	public boolean isAdmin(User user);
}
