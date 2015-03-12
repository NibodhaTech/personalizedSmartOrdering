package com.nibodha.ordering;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

@SpringBootApplication
public class PersonalizedSmartOrderingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalizedSmartOrderingApplication.class, args);
    }
    
    @Bean
	public ServletRegistrationBean jerseyServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new ServletContainer(), "/facebook/*");
		servletRegistrationBean.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
		return servletRegistrationBean;
	}
    
    @Bean
	public MongoTemplate mongoTemplate() throws Exception {

		MongoTemplate mongoTemplate = new MongoTemplate(new MongoClient(
				"127.0.0.1"), "smartorder");
		return mongoTemplate;

	}
}
