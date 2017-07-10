package com.barath.app;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


public class RestReceiverService implements GenericSubscriber{

	@Override
	public Optional<ResponseEntity<String>> subscribeMultipart(MultipartFile file) {
		
		//To be discussed on what can be done with the file received 
		// to persist the file ??
		return Optional.empty();
	}

}
