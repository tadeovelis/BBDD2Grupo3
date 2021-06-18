package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.OnDeliveryPayment;

public interface OnDeliveryPaymentRepository extends CrudRepository<OnDeliveryPayment, Long> {

	public Optional<OnDeliveryPayment> findByName(String name);

	@Query("select pur.paymentMethod from Purchase pur "
			+ "order by (pur.paymentMethod.promisedAmount - ((pur.quantity * pur.productOnSale.price) + pur.deliveryMethod.cost)) desc ")
	public List<OnDeliveryPayment> getMoreChangeOnDeliveryMethod(int i, Pageable pageable);
	
}
