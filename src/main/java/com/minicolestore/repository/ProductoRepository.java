package com.minicolestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.minicolestore.Producto;

@Repository
public interface ProductoRepository extends CrudRepository<Producto, Long>{

}