package com.barath.app;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;


@RestController
public class AppController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private GenericPublisher publisher;
	
	
	@PostMapping(value="/uploadDoc",consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> captureDocument(MultipartFile file){
		
		ResponseEntity<String> response=null;
		if(file !=null && LOGGER.isInfoEnabled()){
			LOGGER.info("Publisher File received {}",file.getOriginalFilename());
			LOGGER.info("Publisher File Size {}",file.getSize());
			Optional<ResponseEntity<String>> output=publisher.publishMultipart(file);
			if( output.isPresent()){
				response=output.get();
			}
		}else{
			response=new ResponseEntity<String>("No File received", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

}
