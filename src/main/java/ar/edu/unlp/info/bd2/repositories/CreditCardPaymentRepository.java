package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.CreditCardPayment;

public interface CreditCardPaymentRepository extends CrudRepository<CreditCardPayment, Long> {

	public Optional<CreditCardPayment> findByName(String name);
	
}
