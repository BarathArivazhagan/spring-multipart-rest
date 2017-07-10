package com.barath.app;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;




public class RestPublisherService implements GenericPublisher{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(RestPublisherService.class);
	
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
			LOGGER.error("Rest service exception {}",e);
		}	
		
		return Optional.of(response);
	}
	
	
	protected ResponseEntity<String> invokeRestCall(String url,HttpMethod httpMethod, HttpEntity<?> requestEntity) throws RestClientException, URISyntaxException{
		
		if(LOGGER.isInfoEnabled()){
			LOGGER.info("Request Url {}   Http method {}  Request Entity {}",url,httpMethod,requestEntity);
		}
		return restTemplate.exchange(new URI(url), httpMethod, requestEntity, String.class);
	
	}

}
