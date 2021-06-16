package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.OnDeliveryPayment;

public interface OnDeliveryPaymentRepository extends CrudRepository<OnDeliveryPayment, Long> {

	public Optional<OnDeliveryPayment> findByName(String name);
	
}
