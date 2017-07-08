package com.barath.app;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class FeignPublisherService implements GenericPublisher{
	
	@Autowired
	private SubscriberClient client;

	@Override
	public Optional<ResponseEntity<String>> publishMultipart(MultipartFile file) {
		
		
		ResponseEntity<String> response= client.captureDocument(file);
		if(response !=null && response.getStatusCode().is2xxSuccessful()){
			return Optional.of(new ResponseEntity<String>(HttpStatus.OK));
		}else{
			return Optional.empty();	
		}
	}

}
