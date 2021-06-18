package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.DeliveryMethod;

public interface DeliveryMethodRepository extends CrudRepository<DeliveryMethod, Long> {

	public Optional<DeliveryMethod> findByName(String name);

	@Query("select pur.deliveryMethod from Purchase pur "
			+ "group by pur.deliveryMethod "
			+ "order by count(*) desc")
	public List<DeliveryMethod> getMostUsedDeliveryMethod(int i, Pageable pageable);
	
}
