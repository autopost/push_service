package com.globallogic.push_service_poc.controller;

import com.globallogic.push_service_poc.bo.PaymentPredictor;
import com.globallogic.push_service_poc.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.text.ParseException;

@Controller
//@RequestMapping("/hello")
public class HelloController {

    @Inject
    PaymentPredictor paymentPredictor;


	@RequestMapping(value = "/hello",method = RequestMethod.GET)
	public String printWelcome(ModelMap model) throws ParseException{

        User user = new User(11223344l, "Controller@test.com", "pass", "21111111");
     //   if (user.getPaymentList().size()>0) {
     //       PaymentPredictor.predict(user);
     //   }
        model.addAttribute("message",user.getUserEmail());
		return "hello_test";
	}
}