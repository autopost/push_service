package com.globallogic.push_service_poc.demo.controller;

import com.globallogic.push_service_poc.demo.bo.PaymentPredictor;
import com.globallogic.push_service_poc.demo.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

   @RequestMapping(value="/{userId}",
            method= RequestMethod.GET)

    public @ResponseBody Double paymentsForUser(@PathVariable("userId") String userId, Model model) {

        CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = queryBuilder.createQuery(User.class);
        Root<User> userRoot = criteria.from(User.class );
        criteria.select(userRoot);
        criteria.where(queryBuilder.equal(userRoot.get(User_.userId),userId));
        List<Payment> paymentList = em.createQuery(criteria).getResultList().get(0).getPaymentList(); //TODO: move to service layer , remove dummy logic

       PaymentPredictor paymentPredictor = new PaymentPredictor();
       return paymentPredictor.predict(em.createQuery(criteria).getResultList().get(0));
    }



}

