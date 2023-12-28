package com.shopme.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    Logger logger = LoggerFactory.getLogger(MvcConfig.class);
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirName = "user-photos";
        Path userPhotosDir = Paths.get(dirName);

        String userPhotosPath = userPhotosDir.toFile().getAbsolutePath();

//        registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/"+ userPhotosPath + "/");
//        registry.addResourceHandler("/"+dirName+"/**" ).addResourceLocations("file:"+userPhotosPath+"/");
//        registry.addResourceHandler(dirName + "/**").addResourceLocations("file:"+ userPhotosPath + "/");
//        registry.addResourceHandler(dirName + "/**").addResourceLocations(userPhotosPath + "/");
        registry.addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:" + userPhotosPath + "/");
        logger.info("file:" + userPhotosPath+"/");
//        registry.addResourceHandler("/" + dirName + "/**")
//                .addResourceLocations("file://" + userPhotosPath + "/");

    }
}
