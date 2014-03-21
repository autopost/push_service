package com.google.android.gcm.demo;

import com.google.android.gcm.demo.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertNotNull;

/**
 * Created by VladyslavPrytula on 3/20/14.
 */
public class userIT {

        private static EntityManagerFactory emf =  Persistence.createEntityManagerFactory("mongoDBUnit2");

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
            User user = em.find(User.class,1001L);

            Assert.assertEquals("pass01",user.getUserPassword() );
        }


        @Test
        public void shouldCreateUser() throws Exception {

            // Creates an instance of book
            User user= new User(1111, "user@test.com", "pass01",
                    "11111111");

            // Persists the book to the database
            tx.begin();
            em.persist(user);
            tx.commit();
            assertNotNull("ID should not be null", user.getUserId());

            // Retrieves all the books from the database
        }



}
