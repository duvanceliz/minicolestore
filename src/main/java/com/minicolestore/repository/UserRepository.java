package com.minicolestore.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.minicolestore.User;

@Repository("userrepository")
public interface UserRepository extends CrudRepository<User, Long>  {
	
    public Optional<User> findByUsername(String username);
    public Optional<User> findByEmailIgnoreCase(String email);
   
}