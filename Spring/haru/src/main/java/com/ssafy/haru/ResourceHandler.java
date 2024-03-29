package com.ssafy.haru;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceHandler implements WebMvcConfigurer {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceHandler.class);

    @Value("${uploadPath1}")
    String uploadPath1;

    @Value("${uploadPath2}")
    String uploadPath2;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	logger.info("uploadPath : {}", uploadPath1);
    	//imgs/menu로 요청이 올 때 static/imgs/menu, uploadPath에서 찾는다. 
    	registry.addResourceHandler("/imgs/diary/**").addResourceLocations( "classpath:/static/imgs/diary/", uploadPath1);
        registry.addResourceHandler("/imgs/user/**").addResourceLocations("classpath:/static/imgs/user/", uploadPath2);
    }
}
