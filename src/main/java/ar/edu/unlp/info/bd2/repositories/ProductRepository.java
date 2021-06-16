package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

	public Optional<Product> findByName(String name);
	
}