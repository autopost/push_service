package com.google.android.gcm.demo.controller;

import com.google.android.gcm.demo.bo.Predictor;
import com.google.android.gcm.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @PersistenceContext(unitName = "mongoDBUnit2")
    private EntityManager em;

    @Inject
    Predictor<User> paymentPredictor;


	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws ParseException{

        User user = new User(11223344l, "Controller@test.com", "pass", "21111111");
        if (user.getPaymentList().size()>0) {
            paymentPredictor.predict(user);
        }
        model.addAttribute("message",user.getUserEmail());//"Hello world!");
		return "hello";
	}
}