package ar.edu.unlp.info.bd2.repositories;

import java.util.List;

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


public class MLRepository{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(Object o) {
        try {
        	this.sessionFactory.getCurrentSession().saveOrUpdate(o);	
        }
        catch (Exception e) {
        	this.sessionFactory.getCurrentSession().clear();
        	throw e;
        }
    }

	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	
	public User findUserByEmail(String email) {
		String hql = "from User where email = :email ";
		Query query = getSession().createQuery(hql);
		query.setParameter("email", email);
		List<User> users = query.getResultList();
		return !users.isEmpty() ? users.get(query.getFirstResult()) : null;
	}
	
	public User findUserById(Long id) {
		String hql = "from User where id = :id ";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		List<User> users = query.getResultList();
		return !users.isEmpty() ? users.get(query.getFirstResult()) : null;
	}
	
	public Category findCategoryByName(String name) {
		String hql = "from Category where name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		List<Category> categories = query.getResultList();
		return !categories.isEmpty() ? categories.get(query.getFirstResult()) : null;
	}
	
	public Provider findProviderByCuit(long cuit) {
		String hql = "from Provider where cuit = :cuit";
		Query query = getSession().createQuery(hql);
		query.setParameter("cuit", cuit);
		List<Provider> providers = query.getResultList();
		return !providers.isEmpty() ? providers.get(query.getFirstResult()) : null;
	}
	
	public Product findProductByName(String name) {
		String hql = "from Product where name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		List<Product> products = query.getResultList();
		return !products.isEmpty() ? products.get(query.getFirstResult()) : null;
	}
	
	public DeliveryMethod findDeliveryMethodByName(String name) {
		String hql = "from DeliveryMethod where name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		List<DeliveryMethod> deliveryMethods = query.getResultList();
		return !deliveryMethods.isEmpty() ? deliveryMethods.get(query.getFirstResult()) : null;
	}
	
	public CreditCardPayment findCreditCardPaymentByName(String name) {
		String hql = "from CreditCardPayment where name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		List<CreditCardPayment> creditCardPayments = query.getResultList();
		return !creditCardPayments.isEmpty() ? creditCardPayments.get(query.getFirstResult()) : null;
	}
	
	public OnDeliveryPayment findOnDeliveryPaymentByName(String name) {
		String hql = "from OnDeliveryPayment where name = :name";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		List<OnDeliveryPayment> onDeliveryPayments = query.getResultList();
		return !onDeliveryPayments.isEmpty() ? onDeliveryPayments.get(query.getFirstResult()) : null;
	}

	/*
	 * Te devuelve una lista con todos los ProductsOnSale que matcheen
	 * con product y provider
	 * Si no hay ninguno devuelve null
	 */
	public List<ProductOnSale> findProductsOnSaleByProductAndProvider(Product product, Provider provider) {
		String hql = "from ProductOnSale where product_id = :product_id and provider_id = :provider_id";
		Query query = getSession().createQuery(hql);
		query.setParameter("product_id", product.getId());
		query.setParameter("provider_id", provider.getId());
		List<ProductOnSale> productsOnSale = query.getResultList();
		return !productsOnSale.isEmpty() ? productsOnSale : null;
	}
	
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
        return this.sessionFactory.getCurrentSession().createQuery("SELECT MAX(pr.price),DISTINCT(prod.id) FROM Product AS prod INNER JOIN ProductOnSale AS pr ON(prod.id = pr.product)").setMaxResults(3).list();
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
}