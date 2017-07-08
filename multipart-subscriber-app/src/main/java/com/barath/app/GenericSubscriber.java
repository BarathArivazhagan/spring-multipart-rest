package com.barath.app;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface GenericSubscriber {
	
	Optional<ResponseEntity<String>>  subscribeMultipart(MultipartFile file);


}
