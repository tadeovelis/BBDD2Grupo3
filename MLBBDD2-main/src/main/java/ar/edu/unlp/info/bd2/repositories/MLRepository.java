package ar.edu.unlp.info.bd2.repositories;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.unlp.info.bd2.model.Category;
import ar.edu.unlp.info.bd2.model.Product;
import ar.edu.unlp.info.bd2.model.Provider;
import ar.edu.unlp.info.bd2.model.User;

public class MLRepository{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void save(Object o) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(o);
    }

	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	/*
	public User createUser(User user){
		Long id = (Long)this.sessionFactory.getCurrentSession().save(user);
		return this.findUserById(id);
	}
	
	
	public User persistUser(User user){
		Long id = (Long)this.sessionFactory.getCurrentSession().save(user);
		return this.findUserById(id);
	}
	*/
	
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
}