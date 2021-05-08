package ar.edu.unlp.info.bd2.services;

import ar.edu.unlp.info.bd2.config.AppConfig;
import ar.edu.unlp.info.bd2.config.HibernateConfiguration;
import ar.edu.unlp.info.bd2.model.*;
import ar.edu.unlp.info.bd2.repositories.MLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, HibernateConfiguration.class}, loader = AnnotationConfigContextLoader.class)
@Transactional
@Rollback(false)
public class MLServiceTestCase {

    @Autowired
    private MLService service;
    
    @Test
    public void testCreateUser() throws MLException{
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1982);
        cal.set(Calendar.MONTH, Calendar.MAY);
        cal.set(Calendar.DAY_OF_MONTH, 17);
        Date dob = cal.getTime();
        User u = this.service.createUser("federico.orlando@info.unlp.edu.ar", "Federico Orlando", "pas$w0rd", dob);
        assertNotNull (u.getId());
        assertEquals("Federico Orlando",u.getFullname());
        Optional<User> us = this.service.getUserByEmail("federico.orlando@info.unlp.edu.ar");
        if (!us.isPresent()) {
            throw new MLException("User doesn't exists");
        }
        User user = us.get();
        assertNotNull (user.getId());
        assertEquals("Federico Orlando",user.getFullname());
        assertEquals(dob, user.getDayOfBirth());
        assertEquals("pas$w0rd", user.getPassword());
        //MLException ex = assertThrows(MLException.class, () -> this.service.createUser("federico.orlando@info.unlp.edu.ar", "Federico Orlando", "pas$w0rd", dob));
        //assertEquals("Constraint Violation",ex.getMessage());
    }
    
    @Test
    public void testCreateCategory() throws MLException {
        Category c = this.service.createCategory("Hogar");
        assertNotNull(c.getId());
        assertEquals("Hogar",c.getName());
        Optional<Category> oc = this.service.getCategoryByName("Hogar");
        if (! oc.isPresent()) {
            throw new MLException("Category not found");
        }
        Category cat = oc.get();
        assertNotNull(cat.getId());
        assertEquals("Hogar",cat.getName());
    }
    
    @Test
    public void testCreateProvider() throws MLException {
        Provider p = this.service.createProvider("Philips",30715589634L);
        assertNotNull (p.getId());
        assertEquals("Philips", p.getName());
        Optional<Provider> prov = this.service.getProviderByCuit(30715589634L);
        if (!prov.isPresent()) {
            throw new MLException("Provider doesn't exists");
        }
        Provider provider = prov.get();
        assertNotNull (provider.getId());
        assertEquals("Philips", provider.getName());
        //MLException ex = assertThrows(MLException.class, () -> this.service.createProvider("Philips",30715589634L));
        //assertEquals("Constraint Violation",ex.getMessage());
    }
    
    @Test
    public void testCreateProduct() throws MLException {
        Category cat = this.service.createCategory("Hogar");
        assertNotNull(cat.getId());
        Product prod = this.service.createProduct("Lamparita led 7w fria", Float.valueOf(40.5F), cat);
        assertNotNull(prod.getId());
        assertEquals(40.5F, (float) prod.getWeight());
        Optional<Product> p = this.service.getProductByName("Lamparita led 7w fria");
        if (!p.isPresent()) {
            throw new MLException("Product doesn't exists");
        }
        Product product = p.get();
        assertNotNull(product.getId());
        assertEquals(Float.valueOf(40.5F), product.getWeight());
        assertEquals("Hogar",product.getCategory().getName());
        //MLException ex = assertThrows(MLException.class, () -> this.service.createProduct("Lamparita led 7w fria", Float.valueOf(40.5F), cat));
        //assertEquals("Constraint Violation",ex.getMessage());
    }

    @Test
    public void testCreateDeliveryMethod() throws MLException {
        DeliveryMethod dm = this.service.createDeliveryMethod("Moto menos 1kg", 250.0F, 0.01F, 0.9999F);
        assertNotNull(dm.getId());
        assertEquals(Float.valueOf(250.0F),dm.getCost());
        assertEquals(Float.valueOf(0.9999F),dm.getEndWeight());
        Optional<DeliveryMethod> del = this.service.getDeliveryMethodByName("Moto menos 1kg");
        if (!del.isPresent()) {
            throw new MLException("Delivery Method doesn't exists");
        }
        DeliveryMethod d = del.get();
        assertNotNull(d.getId());
        assertEquals(Float.valueOf(250.0F),d.getCost());
        assertEquals(Float.valueOf(0.01F),d.getStartWeight());
    }
}
