package ar.edu.unlp.info.bd2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findByEmail(String email);
	
	public User save(User user);

	@Query("select pur.client from Purchase pur "
			+ "where ((pur.quantity * pur.productOnSale.price) + pur.deliveryMethod.cost) > ?1 "
			+ "order by pur.client.email asc")
	public List<User> getUsersSpendingMoreThanInPurchase(Float amount);

	@Query("select pur.client from Purchase pur "
			+ "group by pur.client "
			+ "having ?1 < sum((pur.quantity * pur.productOnSale.price) + pur.deliveryMethod.cost)")
	public List<User> getUsersSpendingMoreThan(Double amount);

	@Query("select pur.client from Purchase pur "
			+ "group by pur.client "
			+ "order by count(*) desc")
	public List<User> getTopNUsersMorePurchase(int n, Pageable pageable);

	@Query("select p from Purchase pur join pur.productOnSale.product as p "
			+ "group by p "
			+ "order by count(*) desc")
	public List<Product> getBestSellingProduct(int i, Pageable pageable);
	
}
