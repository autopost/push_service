package com.google.android.gcm.demo.bo;

import com.google.android.gcm.demo.entity.User;
import com.google.android.gcm.demo.entity.User_;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by VladyslavPrytula on 3/24/14.
 */
public class PaymentPredictorTest {

    private static EntityManagerFactory emf =  Persistence.createEntityManagerFactory("mongoDBUnit1");

    private EntityManager em;
    @Before
    public void setUp(){
        em = emf.createEntityManager();
    }

    @After
    public void tearDown(){
        if (em != null) em.close();
    }

    @Test
    public void testTestRun(){
        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = queryBuilder.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class );
        criteria.select(userRoot);
        criteria.where(queryBuilder.equal(userRoot.get(User_.userId),9999l));
        List<User> userQueried = em.createQuery(criteria).getResultList();

        PaymentPredictor paymentPredictor = new PaymentPredictor();

        em.detach(userQueried.get(0));

        paymentPredictor.predict(userQueried.get(0));

    }

    @Test
    public void testPredict(){

    }


}
