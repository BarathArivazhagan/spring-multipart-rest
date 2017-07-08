package com.barath.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {
	
	@Profile("rest")
	@Bean(name="multipartTemplate")
	public RestTemplate restTemplate(){
		
		List<HttpMessageConverter<?>> messageConverters=new ArrayList<>();
		messageConverters.add(new FormHttpMessageConverter());
		RestTemplate restTemplate=new RestTemplate(messageConverters);
		return restTemplate;
	}
	
	@Bean
	@Profile("rest")
	public RestReceiverService receiverService(){
		return new RestReceiverService();
	}

}
