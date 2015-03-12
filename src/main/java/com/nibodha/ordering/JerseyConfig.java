package com.nibodha.ordering;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig{
	
	public JerseyConfig(){
		register(RequestContextFilter.class);
		packages("com.nibodha.ordering.facebook.rest");
//		register(LoggingFilter.class);
	}
	
}
