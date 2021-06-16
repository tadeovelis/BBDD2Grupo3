package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.DeliveryMethod;

public interface DeliveryMethodRepository extends CrudRepository<DeliveryMethod, Long> {

	public Optional<DeliveryMethod> findByName(String name);
	
}
