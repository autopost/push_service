package com.google.android.gcm.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Created by VladyslavPrytula on 4/12/14.
 */
@Configuration
@EnableWebMvc //this is the same as <mvc:annotation-driven/>
@Import({DataBaseConfig.class})
@ComponentScan(basePackages = {"com.google.android.gcm.demo"}) //his is the same as <context:component-scan base-package=”
/**
 *  Extend the class to use WebMvcConfigurerAdapter.
 *  This adds stub implementations from the WebMvcConfigurer interface which is used by @EnableWebMVC.
 *  It also gives us a chance to override resources and the default handler.
 */

public class AppConfig extends WebMvcConfigurerAdapter{

    //Set default servlet handler, this is the same as <mvc:default-servlet-handler/>
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    // Add bean for InternalResourceViewResolver
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

}
