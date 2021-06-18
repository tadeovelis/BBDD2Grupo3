package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.ProductOnSale;
import ar.edu.unlp.info.bd2.model.Provider;

public interface ProductOnSaleRepository extends CrudRepository<ProductOnSale, Long> {

	public Optional<ProductOnSale> findById(Long id);

	@Query("from ProductOnSale pos where pos.product = ?1 and pos.provider = ?2 and pos.finalDate is null")
	public List<ProductOnSale> getLastProductOnSaleForProductAndProvider(Product product, Provider provider, Pageable pageable);

	@Query("select distinct pur.productOnSale from Purchase pur "
			+ "where pur.dateOfPurchase = ?1")
	public List<ProductOnSale> getSoldProductsOn(Date day);
	
}
