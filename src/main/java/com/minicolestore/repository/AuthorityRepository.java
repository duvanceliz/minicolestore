package com.minicolestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.minicolestore.Authority;



@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long>{

}
