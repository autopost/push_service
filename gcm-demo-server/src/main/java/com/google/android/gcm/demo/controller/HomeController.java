package com.google.android.gcm.demo.controller;

import com.google.android.gcm.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;

/**
 * Created by VladyslavPrytula on 4/14/14.
 */
@Controller
public class HomeController {
    @RequestMapping(value = "/homecontroller",method = RequestMethod.GET)
    public String printWelcome(ModelMap model) throws ParseException {
        User user = new User(11223344l, "Controller@test.com", "pass", "21111111");
        //   if (user.getPaymentList().size()>0) {
        //       PaymentPredictor.predict(user);
        //   }
        model.addAttribute("message",user.getUserEmail());
        return "hello";
    }
}
