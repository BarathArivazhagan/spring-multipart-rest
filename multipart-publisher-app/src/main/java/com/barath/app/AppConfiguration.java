package com.barath.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppConfiguration {
	
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
				
				if(response !=null && !response.getStatusCode().is2xxSuccessful()){
					return true;
				}
				return false;
			}
			
			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				
				log.error("Error in Rest Handler {} ",response.getBody());
				
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
