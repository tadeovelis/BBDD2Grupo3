package ar.edu.unlp.info.bd2.repositories;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ar.edu.unlp.info.bd2.model.Category;
import ar.edu.unlp.info.bd2.model.CreditCardPayment;
import ar.edu.unlp.info.bd2.model.DeliveryMethod;
import ar.edu.unlp.info.bd2.model.OnDeliveryPayment;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.ProductOnSale;
import ar.edu.unlp.info.bd2.model.Provider;
import ar.edu.unlp.info.bd2.model.User;
import ar.edu.unlp.info.bd2.model.Purchase;


public class MLRepositoryStatistics extends CommonRepository{	
	

	public List<Purchase> getAllPurchasesMadeByUser(String username) {
		String hql = "from Purchase pur where pur.client.email = :username";
		Query query = getSession().createQuery(hql);
		query.setParameter("username", username);
		List<Purchase> purchases = query.getResultList();
		return !purchases.isEmpty() ? purchases : null;
	}
	
	public List<User> getUsersSpendingMoreThanInPurchase(Float amount){
		String hql = "select pur.client from Purchase pur "
					+ "where ((pur.quantity * pur.productOnSale.price) + pur.deliveryMethod.cost) > :amount "
					+ "order by pur.client.email asc";		
		Query query = getSession().createQuery(hql);
		query.setParameter("amount", amount);
		List<User> users = query.getResultList();
		return !users.isEmpty() ? users : null;	
	}
	
	public List<User> getUsersSpendingMoreThan(Float amount) {
		String hql = "select pur.client from Purchase pur "
					+ "group by pur.client "
					+ "having "+amount+" < sum((pur.quantity * pur.productOnSale.price) + pur.deliveryMethod.cost)";
		Query query = getSession().createQuery(hql);
		/*
		 * Con parámetro no funcionaba, porque la sum() era un Double y el amount un Float,
		 * y daba error de cast
		 * 
		 * query.setParameter("amount", amount);
		 */
		List<User> users = query.getResultList();
		return !users.isEmpty() ? users : null;
	}
	
	public List<Product> getTop3MoreExpensiveProducts() {
        String hql = "select pos.product from ProductOnSale pos "
        			+ "group by pos.product "
        			+ "order by max(pos.price) desc";
        Query query = getSession().createQuery(hql);
        query.setMaxResults(3);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : null;
    }

	public List <Product> getProductForCategory (Category category) {
		String hql = "from Product p where p.category = :category";
	    Query query = getSession().createQuery(hql);
	    query.setParameter("category", category);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : null;
	}
	
	public List <Purchase> getPurchasesForProvider(Long cuit){
		String hql = "from Purchase pur where pur.productOnSale.provider.cuit = :cuit";
	    Query query = getSession().createQuery(hql);
	    query.setParameter("cuit", cuit);
		List<Purchase> purchases = query.getResultList();
		return !purchases.isEmpty() ? purchases : null;
	}
	
	public List<Purchase> getPurchasesInPeriod(Date startDate, Date endDate) {
		String hql = "from Purchase pur where pur.dateOfPurchase between :id and :ed";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", startDate);
		query.setParameter("ed", endDate);
		List<Purchase> purchases = query.getResultList();
		return !purchases.isEmpty() ? purchases : null;
	}
	
	public List<Product> getProductsOnePrice() {
		String hql = "select pos.product from ProductOnSale pos "
					+ "group by pos.product "
					+ "having count(*) = 1";
		Query query = getSession().createQuery(hql);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : null;
	}

	public Provider getProviderLessExpensiveProduct() {
		String hql = "select pos.provider from ProductOnSale pos "
					+ "where pos.finalDate is null "
					+ "order by pos.price";
		Query query = getSession().createQuery(hql);
		query.setMaxResults(1);
		List<Provider> providers = query.getResultList();
		return !providers.isEmpty() ? providers.get(query.getFirstResult()) : null;
	}

	public List<Provider> getProvidersDoNotSellOn(Date day) {
		String hql = "from Provider pro where pro.id not in ("
						+ "select pur.productOnSale.provider.id from Purchase pur "
						+ "where pur.dateOfPurchase = :day)";
		Query query = getSession().createQuery(hql);
		query.setParameter("day", day);
		List<Provider> providers = query.getResultList();
		return !providers.isEmpty() ? providers : null;
	}

	public List<ProductOnSale> getSoldProductsOn(Date day) {
		String hql = "select distinct pur.productOnSale from Purchase pur "
					+ "where pur.dateOfPurchase = :day ";
		Query query = getSession().createQuery(hql);
		query.setParameter("day", day);
		List<ProductOnSale> productsOnSale = query.getResultList();
		return !productsOnSale.isEmpty() ? productsOnSale : null;
	}
	
	public List<Product> getProductsNotSold() {
		String hql = "from Product p where p not in ("
						+ "select distinct pur.productOnSale.product from Purchase pur)";
		Query query = getSession().createQuery(hql);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : null;
	}

	public DeliveryMethod getMostUsedDeliveryMethod() {
		String hql = "select pur.deliveryMethod from Purchase pur "
					+ "group by pur.deliveryMethod "
					+ "order by count(*) desc";
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		query.setMaxResults(1);
		List<DeliveryMethod> deliveryMethods = query.getResultList();
		return !deliveryMethods.isEmpty() ? deliveryMethods.get(query.getFirstResult()) : null;
	}

	public Product getHeaviestProduct() {
		String hql = "from Product order by weight desc";
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		query.setMaxResults(1);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products.get(query.getFirstResult()) : null;
	}

	public Category getCategoryWithLessProducts() {
		String hql = "select p.category from Product p "
					+ "group by p.category "
					+ "order by count(*)";
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		query.setMaxResults(1);
		List<Category> categories = query.getResultList();
		return !categories.isEmpty() ? categories.get(query.getFirstResult()) : null;
	}

	public List<User> getTopNUsersMorePurchase(int n) {
		String hql = "select pur.client from Purchase pur "
					+ "group by pur.client "
					+ "order by count(*) desc";
		Query query = getSession().createQuery(hql);
		query.setMaxResults(n);
		return query.getResultList();
	}
	
	public Product getBestSellingProduct() {
		String hql = "select p from Purchase pur join pur.productOnSale.product as p "
					+ "group by p "
					+ "order by count(*) desc";
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		query.setMaxResults(1);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products.get(query.getFirstResult()) : null;
	}
	
	public List<Provider> getTopNProvidersInPurchases(int n) {
		String hql = "select pro from Purchase pur join pur.productOnSale.provider as pro "
					+ "group by pro "
					+ "order by count(*) desc";
		Query query = getSession().createQuery(hql);
		// Para que me traiga solo los n primeros
		query.setMaxResults(n);
		List<Provider> providers = query.getResultList();
		return !providers.isEmpty() ? providers : null;
	}
	
	// Para realizar el testGetMoreChangeOnDeliveryMethod
	public Purchase getPurchaseOfOnDeliveryPayment(Long odp_id) {
		String hql = 
				"SELECT pu FROM Purchase pu WHERE pu.paymentMethod = " + odp_id;
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		List<Purchase> purchases = query.getResultList();
		return !purchases.isEmpty() ? purchases.get(query.getFirstResult()) : null;
	}
	
	// Para realizar el testGetMoreChangeOnDeliveryMethod
	public List<OnDeliveryPayment> getAllOnDeliveryPayment() {
		String hql = 
				"SELECT odp FROM OnDeliveryPayment odp";
		Query query = getSession().createQuery(hql);
		List<OnDeliveryPayment> onDeliveryPayments = query.getResultList();
		return !onDeliveryPayments.isEmpty() ? onDeliveryPayments : null;
	}
	
	
	// Para realizar el testGetProductWithMoreThan20percentDiferenceInPrice
	public List<Product> getAllProducts() {
		String hql = 
				"SELECT p FROM Product p";
		Query query = getSession().createQuery(hql);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : null;
	}
	
	// Para realizar el testGetProductWithMoreThan20percentDiferenceInPrice
	public List<ProductOnSale> getProductOnSaleOrderedByPricesForProduct(Long id) {
		String hql = 
				"SELECT pos FROM ProductOnSale pos "
				+ "WHERE pos.product = " + id +" "
				+ "ORDER BY pos.price";
		Query query = getSession().createQuery(hql);
		List<ProductOnSale> productsOnSale = query.getResultList();
		return productsOnSale;
	}
}
