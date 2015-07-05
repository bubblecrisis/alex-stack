package com.paymee.service;

import com.paymee.service.resource.AggregateIdentityResource;
import com.paymee.service.resource.Hello;
import com.paymee.service.resource.ValidationResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
		this.register(Hello.class);
		this.register(ValidationResource.class);
		this.register(AggregateIdentityResource.class);
    }

	@Bean
	public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
