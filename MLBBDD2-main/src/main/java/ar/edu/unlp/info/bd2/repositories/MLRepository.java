package ar.edu.unlp.info.bd2.repositories;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
	
	public User createUser(User user){
		Long id = (Long)this.sessionFactory.getCurrentSession().save(user);
		return this.findUserById(id);
	}
	
	public User persistUser(User user){
		Long id = (Long)this.sessionFactory.getCurrentSession().save(user);
		return this.findUserById(id);
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
}