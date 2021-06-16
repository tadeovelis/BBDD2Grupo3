package ar.edu.unlp.info.bd2.repositories;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CommonRepository {
	@Autowired
	protected SessionFactory sessionFactory;
	
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
	
	public String convertDay(Date day) {
    	String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(day);
	}	
}
