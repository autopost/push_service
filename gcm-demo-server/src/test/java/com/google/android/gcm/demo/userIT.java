package com.google.android.gcm.demo;

import com.google.android.gcm.demo.entity.Invoice;
import com.google.android.gcm.demo.entity.Payment;
import com.google.android.gcm.demo.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by VladyslavPrytula on 3/20/14.
 * Integration test for persistence units
 */
public class userIT {

        private static EntityManagerFactory emf =  Persistence.createEntityManagerFactory("mongoDBUnit1");

        private EntityManager em;
        private EntityTransaction tx;

        @Before
        public void initEntityManager() throws Exception {
            em = emf.createEntityManager();
            tx = em.getTransaction();
        }
        @After
        public void closeEntityManager() throws Exception {
            if (em != null) em.close();
        }

        @Test
        public void shouldUserPassword() throws Exception {
            User user = em.find(User.class,1111L);

            Assert.assertEquals("pass01",user.getUserPassword() );
        }


        @Test
        public void shouldCreateUser() throws Exception {

            // Creates an instance of user
            User user= new User(1111, "user@test.com", "pass01",
                    "11111111");

            // Persists the book to the database
            tx.begin();
            em.persist(user);
            tx.commit();
            assertNotNull("ID should not be null", user.getUserId());

            // Retrieves all the instances from the database
            //TODO:implement comment
        }

        @Test
        public void shouldCreateAllEntitiesAndReadThem() throws ParseException {
            // Creates an instance of all entities

            Date startDate =  new SimpleDateFormat("MM/dd/yyyy").parse("05/18/2008");
            Date endDate =  new SimpleDateFormat("MM/dd/yyyy").parse("05/18/2009");
                User user= new User(2111, "user@test.com", "pass02", "21111111");
                Invoice invoice = new Invoice(123l, "testInvoice", 1123d, startDate, endDate, user);
                Payment payment = new Payment(1111,234d)

;

            // Persists entities to the database
            tx.begin();
            em.persist(user);
            em.persist(invoice);
            em.persist(payment);
            tx.commit();
   /*         assertNotNull("ID should not be null", emuser.getUserId());
            assertNotNull("ID should not be null", payment.getPaymentId());
            assertNotNull("ID should not be null", invoice.getInvoiceId());*/
            Invoice testInvoice =em.find(Invoice.class,123l);
            assertNotNull(testInvoice.getInvoiceAmount());
            assertNotNull(testInvoice.getUser().getUserEmail());

        }


        @Test
        public void populateInvoices()
        {}



}
