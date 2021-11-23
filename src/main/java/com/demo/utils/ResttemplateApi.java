/***********************************************************************************
 * FILE: ResttemplateApi.java
 * DESC :
 *   1. Resttemplate API
 * PROJ : Spring Boot Framework. 
 * -------------------------------------------------------------------------------------
 *                  Modification History
 * -------------------------------------------------------------------------------------
 *  DATE           AUTHOR              FUNCTION        DESCRIPTION
 * -------------  -----------------	--------------  --------------------
 * 2021/01/08     Sangyoub Lee                        Description 
 * -------------  -----------------	--------------  --------------------
 ***********************************************************************************/
package com.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ResttemplateApi<T> {
	
	private RestTemplate restTemplate;

    @Autowired
    public ResttemplateApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<T> get(String url, HttpHeaders httpHeaders) {
        return callRestApi(url, HttpMethod.GET, httpHeaders, null, (Class<T>)Object.class);
    }

    public ResponseEntity<T> get(String url, HttpHeaders httpHeaders, Class<T> clazz) {
        return callRestApi(url, HttpMethod.GET, httpHeaders, null, clazz);
    }

    public ResponseEntity<T> post(String url, HttpHeaders httpHeaders, Object body) {
        return callRestApi(url, HttpMethod.POST, httpHeaders, body,(Class<T>)Object.class);
    }

    public ResponseEntity<T> post(String url, HttpHeaders httpHeaders, Object body, Class<T> clazz) {
        return callRestApi(url, HttpMethod.POST, httpHeaders, body, clazz);
    }
    
    public ResponseEntity<T> put(String url, HttpHeaders httpHeaders, Object body) {
        return callRestApi(url, HttpMethod.PUT, httpHeaders, body,(Class<T>)Object.class);
    }
    
    public ResponseEntity<T> put(String url, HttpHeaders httpHeaders, Object body, Class<T> clazz) {
        return callRestApi(url, HttpMethod.PUT, httpHeaders, body, clazz);
    }
    
    public void voidPut(String url, HttpHeaders httpHeaders, Object body) {
    	restTemplate.put(url, new HttpEntity<>(body, httpHeaders));
    }

    private ResponseEntity<T> callRestApi(String url, HttpMethod httpMethod, HttpHeaders httpHeaders, Object body, Class<T> clazz) {
        return restTemplate.exchange(url, httpMethod, new HttpEntity<>(body, httpHeaders), clazz);
    }

}
