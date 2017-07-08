package com.barath.app;

import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AppController {
	
	
	@Autowired
	private GenericSubscriber subscriber;
	
	
	@PostMapping(value="/receiveDoc",consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> receiveDocument(MultipartFile file){
		
		ResponseEntity<String> response=null;	
		if(file !=null && log.isInfoEnabled()){
			log.info("File received {}",file.getOriginalFilename());
			log.info("File Size {}",file.getSize());
			//subscriber.subscribeMultipart(file);
		}else{
			response=new ResponseEntity<String>("No File received", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	
	
	/* @PostMapping(value="/receiveDoc",consumes=MediaType.MULTIPART_FORM_DATA_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> receiveDocument(MultipartHttpServletRequest request){
		
		ResponseEntity<String> response=null;
		Map<String,MultipartFile> map=request.getFileMap();
		System.out.println("Map "+map);
//		if(file !=null && log.isInfoEnabled()){
//			log.info("File received {}",file.getOriginalFilename());
//			subscriber.subscribeMultipart(file);
//		}else{
//			response=new ResponseEntity<String>("No File received", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
		
		return response;
	} */
	
	@PostMapping(value="/receiveDoc1",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> receiveDocument1(MultiValueMap<String, Object> file){
		
		ResponseEntity<String> response=null;
		
		
		System.out.println("File Map "+file);
		
		return response;
	}

}
