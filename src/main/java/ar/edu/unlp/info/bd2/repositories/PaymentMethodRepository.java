package ar.edu.unlp.info.bd2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.PaymentMethod;

public interface PaymentMethodRepository extends CrudRepository<PaymentMethod, Long> {

}
