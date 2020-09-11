package com.eleaning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final long MAX_AGE_SECS = 3600;

//	@Value("${dropbox.accessToken}")
//	private String accessToken;
//
//	@Bean
//	public DbxClientV2 dropboxClient() {
//		DbxRequestConfig config = DbxRequestConfig.newBuilder("elearning-app").build();
//		return new DbxClientV2(config, accessToken);
//	}
//
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("*")
//				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*")
//				.allowCredentials(true).maxAge(MAX_AGE_SECS);
//	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/static/upload/**")
          .addResourceLocations("/static/upload/**")
          .setCachePeriod(3600)
          .resourceChain(true)
          .addResolver(new EncodedResourceResolver());	
    }
}
