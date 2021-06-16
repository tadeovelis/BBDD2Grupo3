package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Provider;

public interface ProviderRepository extends CrudRepository<Provider, Long> {

	public Optional<Provider> findByCuit(long cuit);
	
}
