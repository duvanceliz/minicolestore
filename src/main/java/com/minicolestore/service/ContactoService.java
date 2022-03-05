package com.minicolestore.service;

import java.util.List;

import com.minicolestore.Contacto;

public interface ContactoService {
	
	public abstract List<Contacto> ListAllContacto(); 
    
	public abstract Contacto addContacto(Contacto contacto);
	
	public abstract Contacto getById(int id);
	
}
