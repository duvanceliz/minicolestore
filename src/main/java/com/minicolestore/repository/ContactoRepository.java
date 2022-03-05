package com.minicolestore.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.minicolestore.Contacto;

@Repository("contactorepository")
public interface ContactoRepository extends JpaRepository <Contacto , Serializable > {

}
