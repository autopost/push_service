package com.google.android.gcm.demo.controller;

import com.google.android.gcm.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;

@Controller
@RequestMapping("/hello")
public class HelloController {
    @PersistenceContext(unitName = "mongoDBUnit2")
    private EntityManager em;


	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws ParseException{

        User user = new User(11223344l, "Controller@test.com", "pass", "21111111");

        model.addAttribute("message",user.getUserEmail());//"Hello world!");
		return "hello";
	}
}