package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{

	public Optional<Category> findByName(String name);

	@Query("select p.category from Product p "
			+ "group by p.category "
			+ "order by count(*)")
	public List<Category> getCategoryWithLessProducts(int i, Pageable pageable);
	
}
