package com.barath.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfiguration {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AppConfiguration.class);
	
	@Profile("rest")
	@Bean(name="multipartTemplate")
	public RestTemplate restTemplate(){
		
		List<HttpMessageConverter<?>> messageConverters=new ArrayList<>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		RestTemplate restTemplate=new RestTemplate(messageConverters);
		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				
				return response !=null && !response.getStatusCode().is2xxSuccessful() ? true : false;
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				
				LOGGER.error("Error in Rest Handler {} ",response.getBody());
				
			}
		});
		return restTemplate;
	}
	
	@Bean
	@Profile("rest")
	public RestPublisherService publisherService(){
		return new RestPublisherService();
	}
	
	
	@Bean
	@Profile("feign")
	public FeignPublisherService feignPublisherService(){
		return new FeignPublisherService();
	}

}
