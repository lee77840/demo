package com.demo.config;


import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.demo.constant.Constants;
import com.demo.errorhandler.RestTemplateErrorHandler;
import com.demo.interceptor.LoggingInterceptor;


@Configuration
public class RestTemplateConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        HttpClient client = HttpClientBuilder.create()
                .setMaxConnTotal(Constants.httpclientConTotal)
                .setMaxConnPerRoute(Constants.httpclientConPerRoute)
                .build();

        factory.setHttpClient(client);
        factory.setConnectTimeout(Constants.httpclientConTimeout);
        factory.setReadTimeout(Constants.httpclientReadTimeout);

        // return new RestTemplate(factory);
        RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(factory));
        restTemplate.setInterceptors(Collections.singletonList(new LoggingInterceptor()));
        
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        return restTemplate;
	}
	
}
