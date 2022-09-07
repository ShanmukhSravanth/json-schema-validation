package com.poc.config;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.poc.schema.JsonRequestBodyBeanPostProcessor;
@Configuration
@ComponentScan({"olivecrypto.upi"})
public class JsonConf {
	
		@Bean
		static BeanPostProcessor jsonRequestBodyBeanPostProcessor() {
			return new JsonRequestBodyBeanPostProcessor();
		}
}