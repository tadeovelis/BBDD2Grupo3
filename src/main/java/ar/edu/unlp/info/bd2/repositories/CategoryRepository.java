package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{

	public Optional<Category> findByName(String name);
	
}
