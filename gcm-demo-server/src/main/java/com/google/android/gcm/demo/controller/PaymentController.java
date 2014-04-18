package com.google.android.gcm.demo.controller;

import com.google.android.gcm.demo.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by vladyslavprytula on 4/18/14.
 */
@Controller
@RequestMapping("/payments")
public class PaymentController {

    @PersistenceContext(unitName = "mongoDBUnit2")
    private EntityManager em;

    @RequestMapping(value="/payments",
            method= RequestMethod.GET)
    public @ResponseBody List<Payment> paymentsForUser(@PathVariable("userId") String userId) {

        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = queryBuilder.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class );
        criteria.select(userRoot);
        criteria.where(queryBuilder.equal(userRoot.get(User_.userId),userId));
        List<Payment> paymentList = em.createQuery(criteria).getSingleResult().getPaymentList();

        return paymentList;
    }
}

