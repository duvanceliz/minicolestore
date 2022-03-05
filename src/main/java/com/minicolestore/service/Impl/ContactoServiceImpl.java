package com.minicolestore.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.minicolestore.Contacto;
import com.minicolestore.repository.ContactoRepository;
import com.minicolestore.service.ContactoService;

@Service("contactoservice")
public class ContactoServiceImpl implements ContactoService {

	@Autowired
	@Qualifier("contactorepository")
	private ContactoRepository contactoRepository;
	
	@Override
	public List<Contacto> ListAllContacto() {
		
		return contactoRepository.findAll() ;
	}

	@Override
	public Contacto addContacto(Contacto contacto) {
		
		return contactoRepository.save(contacto);
	}

	@Override
	public Contacto getById(int id) {
		
		return contactoRepository.getById(id);
	}


	

}
