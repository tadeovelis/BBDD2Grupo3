package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

	public Optional<Purchase> findById(Long id);
	
}
