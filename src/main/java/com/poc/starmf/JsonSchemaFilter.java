package com.poc.starmf;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse; 
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
@Component
@Order(1)
public class JsonSchemaFilter implements Filter {
 
 
 
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	
    	//We can check for method POST
         String type=((HttpServletRequest)request).getHeader("Content-Type");
    	 if(type!=null && type.contains("json")){// "application/json\"");//
            request = new RequestWrapper((HttpServletRequest)request);
    	 }
        chain.doFilter(request, response);
    }
 
    public void init(FilterConfig filterConfiguration) throws ServletException {
    }
 
    public void destroy() {
    }
}