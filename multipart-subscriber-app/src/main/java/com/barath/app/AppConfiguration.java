package com.barath.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfiguration extends WebMvcConfigurerAdapter{
	
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
	
/*	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		converters.add(new FormHttpMessageConverter());
		converters.add(new ByteArrayHttpMessageConverter());
		super.configureMessageConverters(converters);
	}*/

}
