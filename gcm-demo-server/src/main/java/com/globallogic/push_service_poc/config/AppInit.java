package com.globallogic.push_service_poc.config;

import com.globallogic.push_service_poc.server.RegisterServlet;
import com.globallogic.push_service_poc.server.ApiKeyInitializer;
import com.globallogic.push_service_poc.server.SendServlet;
import com.globallogic.push_service_poc.server.UnregisterServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by vladyslavprytula on 4/21/14.
 */
public class AppInit implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new ContextLoaderListener(context));
        servletContext.addListener(new ApiKeyInitializer());
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        ServletRegistration.Dynamic registerServlet = servletContext.addServlet("RegisterServlet", new RegisterServlet());
        registerServlet .setLoadOnStartup(1);
        registerServlet .addMapping("/register");

        ServletRegistration.Dynamic sendServlet = servletContext.addServlet("SendServlet", new SendServlet());
        sendServlet .setLoadOnStartup(1);
        sendServlet.addMapping("/send");

        ServletRegistration.Dynamic unregisterServlet = servletContext.addServlet("UnregisterServlet", new UnregisterServlet());
        unregisterServlet .setLoadOnStartup(1);
        unregisterServlet.addMapping("/unregister");

    }

    private AnnotationConfigWebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("AppConfig");
        return context;
    }


}
