package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.ProductOnSale;

public interface ProductOnSaleRepository extends CrudRepository<ProductOnSale, Long> {

	public Optional<ProductOnSale> findById(Long id);
	
}
