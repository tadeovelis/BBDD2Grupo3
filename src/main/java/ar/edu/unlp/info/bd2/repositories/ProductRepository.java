package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

	public Optional<Product> findByName(String name);
	
	public Product findFirstByOrderByWeightDesc();

	@Query("select pos.product from ProductOnSale pos "
			+ "group by pos.product "
			+ "order by max(pos.price) desc")
	public List<Product> getTop3MoreExpensiveProducts(int i, Pageable pageable);
	
}