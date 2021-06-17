package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

	public Optional<Purchase> findById(Long id);

	@Query("from Purchase pur where pur.client.email = ?1")
	public List<Purchase> findAllPurchasesMadeByUser(String username);
	
}
