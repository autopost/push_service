package com.google.android.gcm.demo;

import com.google.android.gcm.demo.entity.Payment;
import com.google.android.gcm.demo.entity.User;
import com.google.android.gcm.demo.entity.User_;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by VladyslavPrytula on 3/23/14.
 */
public class dataPreparationIT {

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
    public void populatePayments()
            //TODO:we do not have a link between payment and user. the quesiton is do we want to have it or not?
    {
        class RandomDateOfTransaction {

            public Date prepareDate(int lowerBound, int upperBound) {

                GregorianCalendar gc = new GregorianCalendar();
                int year = randBetween(lowerBound, upperBound);
                gc.set(gc.YEAR, year);
                int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
                gc.set(gc.DAY_OF_YEAR, dayOfYear);

                //TODO: needs to be logged (gc.get(gc.YEAR) + "-" + gc.get(gc.MONTH) + "-" + gc.get(gc.DAY_OF_MONTH));

                return gc.getTime();
            }

            public int randBetween(int start, int end) {
                return start + (int)Math.round(Math.random() * (end - start));
            }
        }

        List<Payment> paymentsList = new ArrayList<>();


        for (int i =0 ; i < 10; i++){
            paymentsList.add(new Payment(1111l+(long)i,(Double)(Math.random() * 50 + 1),
                    new RandomDateOfTransaction().prepareDate(2012,2013),
                    new RandomDateOfTransaction().prepareDate(2012,2013)));
        }
        User user = new User(9999l, "PaymentOwner@test.com", "pass", "21111111");
        user.setPaymentList(paymentsList);
        // Persists paymentsList to the database

        tx.begin();
        for (Payment pmnt : paymentsList) {
            em.persist(pmnt);
        }
        em.persist(user);
        tx.commit();

        //Retrieve payments from the databaseç
        assertNotNull(em.find(User.class,9999l).getPaymentList().size());


        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = queryBuilder.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class );
        criteria.select(userRoot);
        criteria.where(queryBuilder.equal(userRoot.get(User_.userId),9999l));
        List<User> userQueried = em.createQuery(criteria).getResultList();
        assertEquals(em.find(User.class,9999l).getUserEmail(), userQueried.get(0).getUserEmail());

    }
}
