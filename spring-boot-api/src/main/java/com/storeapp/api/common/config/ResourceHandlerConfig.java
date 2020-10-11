package com.storeapp.api.common.config;

import com.storeapp.api.common.constant.Constant;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ResourceHandlerConfig
 *
 * @author khal
 * @since 2020/09/29
 */
@Configuration
@AutoConfigureAfter(DispatcherServletAutoConfiguration.class)
public class ResourceHandlerConfig implements WebMvcConfigurer {

    private Constant constant;

    /**
     * Default constructor
     */
    public ResourceHandlerConfig (final Constant constant) {
        this.constant = constant;
    }

    /**
     * Resource configuration
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**",
                "swagger-ui.html",
                "/webjars/**")
                .addResourceLocations("file:" + constant.getRootDir(),
                        "file:" + constant.getRootDir() + constant.getStaticDir(),
                        "classpath:/static/",
                        "classpath:/WEB-INF/",
                        "classpath:/META-INF/resources/",
                        "classpath:/META-INF/resources/webjars/");
    }

}
