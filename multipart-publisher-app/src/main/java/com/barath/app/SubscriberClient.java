package com.barath.app;

import java.util.Optional;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;


@FeignClient(name="subscriber-client",url="${service.subscriber.url}",configuration=SubscriberClient.MultipartSupportConfig.class)
public interface SubscriberClient {
	
	
	
	@PostMapping(value="/receiveDoc",consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> uploadDocument(MultipartFile file);
	
	@Configuration
    public class MultipartSupportConfig {

        @Bean
        @Primary
        @Scope("prototype")
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder();
        }
    }

}
