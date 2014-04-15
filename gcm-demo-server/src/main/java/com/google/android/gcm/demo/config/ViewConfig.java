package com.google.android.gcm.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

/**
 * Created by VladyslavPrytula on 4/14/14.
 */
@Configuration
public class ViewConfig {

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configurer = new TilesConfigurer();
        configurer.setDefinitions(new String[]
                { "/WEB-INF/layouts/tiles.xml",
                        "/WEB-INF/views/**/tiles.xml" });
        configurer.setCheckRefresh(true);
        return configurer;
    }
}
