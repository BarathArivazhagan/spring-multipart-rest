package com.barath.app;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestPublisherService implements GenericPublisher{
	
	@Autowired
	@Qualifier("multipartTemplate")
	private RestTemplate restTemplate;
	
	@Value("${service.subscriber.url}")
	private String serviceUrl;
	
	private static final String DOCUMENT_UPLOAD_REST_PATH="/recevieDoc";

	@Override
	public Optional<ResponseEntity<String>> publishMultipart(MultipartFile file) {
		
		ResponseEntity<String> response=null;
		try{
			
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> fileMap=new LinkedMultiValueMap<>();
			fileMap.add("file", FileCopyUtils.copyToByteArray(file.getInputStream()));
			HttpEntity<MultiValueMap> requestEntity=new HttpEntity<MultiValueMap>(fileMap,headers);
			response=invokeRestCall(serviceUrl.concat(DOCUMENT_UPLOAD_REST_PATH), HttpMethod.POST,requestEntity );
			
		}catch (Exception e) {
			log.error("Rest service exception {}",e);
		}	
		
		return Optional.of(response);
	}
	
	
	protected ResponseEntity<String> invokeRestCall(String url,HttpMethod httpMethod, HttpEntity<?> requestEntity) throws RestClientException, URISyntaxException{
		
		if(log.isInfoEnabled()){
			log.info("Request Url {}   Http method {}  Request Entity {}",url,httpMethod,requestEntity);
		}
		ResponseEntity<String> response=restTemplate.exchange(new URI(url), httpMethod, requestEntity, String.class);
		return response;
	}

}
