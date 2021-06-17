package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Long> {

	public Optional<Provider> findByCuit(Long cuit);
	
	public Provider save(Provider provider);

	@Query("select pro from Purchase pur join pur.productOnSale.provider as pro "
			+ "group by pro "
			+ "order by count(*) desc")
	public List<Provider> getTopNProvidersInPurchases(int n, Pageable pageable);
	
}
