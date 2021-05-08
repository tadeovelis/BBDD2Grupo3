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

}
