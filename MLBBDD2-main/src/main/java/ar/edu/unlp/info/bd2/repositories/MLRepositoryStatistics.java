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
		return this.sessionFactory.getCurrentSession().createQuery("SELECT p FROM User u INNER JOIN Purchase p ON (u.id = p.client) WHERE u.email = '"+ username +"'").list();
	}
	/*
	public List<User> getUsersSpendingMoreThanInPurchase(Float amount){
		return this.sessionFactory.getCurrentSession().createQuery("SELECT u FROM ProductOnSale pr inner join Purchase p on (p.productOnSale_id = pr.product_id) inner join User u on (u.id = p.client) where pr.price > '"+ amount +"' order by pr.price ").list();
	}
	
	public List<User> getUsersSpendingMoreThan(Float amount) {
    	return this.sessionFactory.getCurrentSession().createQuery("SELECT u  FROM Purchase p INNER JOIN User u ON(u.id = p.client) WHERE ((p.cost) > '" + amount + "')").list();
    }
		
	public List<Product> getTop3MoreExpensiveProducts() {
        return this.sessionFactory.getCurrentSession().createQuery("SELECT prod "
        		+ "FROM Product AS prod INNER JOIN ProductOnSale AS pr ON(prod.id = pr.product) ORDER BY pr.price ASC").setMaxResults(3).list();
    }
*/
	public List <Product> getProductForCategory (Category category){
		Long cat = category.getId();
		return this.sessionFactory.getCurrentSession().createQuery("SELECT p FROM Product AS p INNER JOIN Category AS c ON (p.category = c.id) WHERE c.id = '" + cat + "'").list();
	}
	
	public List <Purchase> getPurchasesForProvider(Long cuit){
		return this.sessionFactory.getCurrentSession().createQuery("SELECT p FROM Purchase AS p INNER JOIN ProductOnSale AS pro ON (p.productOnSale = pro.id) "
				+ "INNER JOIN Provider AS pr ON (pro.provider = pr.id) WHERE pr.cuit = '" + cuit + "'").list();
	}
	
	public  List <Purchase> getPurchasesInPeriod(Date startDate, Date endDate){
		MLRepository paraConvertirFechas = new MLRepository();
		String date_start = paraConvertirFechas.convertDay(startDate);
		String date_end = paraConvertirFechas.convertDay(endDate);
		return this.sessionFactory.getCurrentSession().createQuery("SELECT p FROM Purchase AS p WHERE p.dateOfPurchase BETWEEN '"+ date_start +"' AND '"+ date_end +"'").list(); 
	}
	
	public List<Product> getProductsOnePrice() {
		String hql = 
				"SELECT p FROM Product p WHERE EXISTS ("
				+ "SELECT pro.product, count(*) "
				+ "FROM ProductOnSale pro INNER JOIN Product p2 ON (pro.product = p2.id) "
				+ "WHERE pro.product = p.id "
				+ "GROUP BY pro.product "
				+ "HAVING count(*) = 1"
			+ ")";
		Query query = getSession().createQuery(hql);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : null;
	}

	public Provider getProviderLessExpensiveProduct() {
		String hql = 
				"SELECT p FROM Provider p INNER JOIN ProductOnSale pos ON "
				+ "(p.id = pos.provider) "
				+ "WHERE pos.price = ("
					+ "SELECT min(price) "
					+ "FROM ProductOnSale"
				+ ")";
		Query query = getSession().createQuery(hql);
		List<Provider> providers = query.getResultList();
		return !providers.isEmpty() ? providers.get(query.getFirstResult()) : null;
	}

	public List<Provider> getProviderDoNotSellOn(Date day) {
		String dayConverted = this.convertDay(day);
		String hql = 
				"SELECT distinct p FROM Provider p INNER JOIN ProductOnSale pos ON (p.id = pos.provider) "
				+ "INNER JOIN Purchase pu ON (pu.productOnSale = pos.id) "
				+ "WHERE pu.dateOfPurchase != '" + dayConverted + "'";
		Query query = getSession().createQuery(hql);
		List<Provider> providers = query.getResultList();
		return !providers.isEmpty() ? providers : null;
	}

	public List<ProductOnSale> getSoldProductsOn(Date day) {
		String dayConverted = this.convertDay(day);
		String hql = 
				"SELECT distinct p FROM ProductOnSale p INNER JOIN Purchase pu ON (p.id = pu.productOnSale) "
				+ "WHERE pu.dateOfPurchase = '" + dayConverted + "'";
		Query query = getSession().createQuery(hql);
		List<ProductOnSale> productsOnSale = query.getResultList();
		return !productsOnSale.isEmpty() ? productsOnSale : null;
	}

	public List<Product> getProductsNotSold() {
		String hql = 
				"SELECT p FROM Product p WHERE not exists ("
				+ "SELECT 1 FROM ProductOnSale pos INNER JOIN Purchase pu ON (pos.id = pu.productOnSale) "
				+ "WHERE pos.product = p.id)";
		Query query = getSession().createQuery(hql);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products : null;
	}

	public DeliveryMethod getMostUsedDeliveryMethod() {
		String hql = 
				"SELECT dm FROM DeliveryMethod dm INNER JOIN Purchase pu ON (dm.id = pu.deliveryMethod) "
				+ "GROUP BY pu.deliveryMethod "
				+ "ORDER BY count(*) DESC";
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		query.setMaxResults(1);
		List<DeliveryMethod> deliveryMethods = query.getResultList();
		return !deliveryMethods.isEmpty() ? deliveryMethods.get(query.getFirstResult()) : null;
	}

	public Product getHeaviestProduct() {
		String hql = 
				"SELECT p FROM Product p ORDER BY p.weight DESC";
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		query.setMaxResults(1);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products.get(query.getFirstResult()) : null;
	}

	public Category getCategoryWithLessProducts() {
		String hql = 
				"SELECT c FROM Category c INNER JOIN Product p ON (c.id = p.category) "
				+ "GROUP BY c.id "
				+ "ORDER BY count(*) ASC";
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		query.setMaxResults(1);
		List<Category> categories = query.getResultList();
		return !categories.isEmpty() ? categories.get(query.getFirstResult()) : null;
	}
	
	public Product getBestSellingProduct() {
		String hql = 
				"SELECT p FROM Product p INNER JOIN ProductOnSale pos ON (p.id = pos.product) "
				+ "INNER JOIN Purchase pu ON (pos.id = pu.productOnSale) "
				+ "GROUP BY p.id "
				+ "ORDER BY count(*) DESC";
		Query query = getSession().createQuery(hql);
		// Para que me traiga el primero solamente
		query.setMaxResults(1);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products.get(query.getFirstResult()) : null;
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
	
}
