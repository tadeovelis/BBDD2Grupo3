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
	
}
