package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.ProductOnSale;
import ar.edu.unlp.info.bd2.model.Provider;

public interface ProductOnSaleRepository extends CrudRepository<ProductOnSale, Long> {

	public Optional<ProductOnSale> findById(Long id);

	@Query("from ProductOnSale pos where pos.product = ?1 and pos.provider = ?2 and pos.finalDate is null")
	public ProductOnSale getLastProductOnSaleForProductAndProvider(Product product, Provider provider);
	
}
