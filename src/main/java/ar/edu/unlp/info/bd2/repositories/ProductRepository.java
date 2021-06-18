package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ar.edu.unlp.info.bd2.model.Category;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.ProductOnSale;

public interface ProductRepository extends CrudRepository<Product, Long>{

	public Optional<Product> findByName(String name);
	
	public Product findFirstByOrderByWeightDesc();

	@Query("select pos.product from ProductOnSale pos "
			+ "group by pos.product "
			+ "order by max(pos.price) desc")
	public List<Product> getTop3MoreExpensiveProducts(int i, Pageable pageable);

	public List<Product> findByCategory(Category category);

	@Query("select pos.product from ProductOnSale pos "
					+ "group by pos.product "
					+ "having count(*) = 1")
	public List<Product> getProductOnePrice();

	@Query("from Product p where p not in ("
			+ "select distinct pur.productOnSale.product from Purchase pur)")
	public List<Product> getProductsNotSold();

	@Query("select pos.product from ProductOnSale pos "
			+ "where pos.finalDate is null "
			+ "group by pos.product "
			+ "having ( (max(pos.price) - min(pos.price)) > (min(pos.price) * 0.2) )")
	public List<Product> getProductWithMoreThan20percentDiferenceInPrice();
	
}