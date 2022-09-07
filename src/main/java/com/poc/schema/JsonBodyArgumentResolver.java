package com.poc.schema;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.fasterxml.jackson.core.JsonParseException;
import com.poc.json.SchemaValidator;
import com.poc.json.schema.annotation.JsonRequestBody;
import com.poc.starmf.RequestWrapper;

public class JsonBodyArgumentResolver implements HandlerMethodArgumentResolver  {

	private RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor;
    private ValidationExceptionHandler validationExceptionHandler;
	 
	public JsonBodyArgumentResolver(RequestResponseBodyMethodProcessor requestResponseBodyMethodProcessor) {
		this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;  
		this.validationExceptionHandler = new ValidationExceptionHandler();
		 

      } 
	
	
	 @Override
	 public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		    
		 Object requestBodyAnnotatedReturnValue = readRequestBodyAnnotatedParameter(parameter, mavContainer, webRequest, binderFactory);

	     String name = Conventions.getVariableNameForParameter(parameter);
	     BindingResult bindingResult = (BindingResult) mavContainer.getModel().get(BindingResult.MODEL_KEY_PREFIX + name);
	      if (bindingResult == null) {
	            bindingResult = createBindingResult(webRequest);
	            mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX +  name, bindingResult);
	      }
	      
	      validate(parameter, webRequest, bindingResult, isThrowingExceptionOnValidationError(parameter));
 	      return requestBodyAnnotatedReturnValue;
	    }
	 
	    private Object readRequestBodyAnnotatedParameter(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
	        return this.requestResponseBodyMethodProcessor.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
	    }
	 
	    @Override
	    public boolean supportsParameter(MethodParameter parameter) {
	        return parameter.hasParameterAnnotation(JsonRequestBody.class);
	    }
	 
	    private BindingResult createBindingResult(WebRequest webRequest) {
	        return new WebRequestBindingResult(webRequest);
	    }
	    private boolean isThrowingExceptionOnValidationError(MethodParameter parameter) {
	        JsonRequestBody annotation = parameter.getParameterAnnotation(JsonRequestBody.class);
	        return annotation.strict();
	    }
	  
	    private void validate(MethodParameter parameter, NativeWebRequest webRequest, BindingResult bindingResult, boolean throwExceptionOnSchemaValidationError) throws IOException {
	        
	    	//int beforeSchemaValidationErrorCount = bindingResult.getErrorCount();
	        String requestBodyJson = getJsonPayload(webRequest);
	        List<Class<?>> types=Arrays.asList(parameter.getMethod().getParameterTypes());
            
           // System.out.println(requestBodyJson);
             
            //We expect only one as Json data is mapped to one object only
            if(!types.isEmpty()) {
            	
                 // System.out.println(types.get(0));
                 //SchemaValidator validator=new SchemaValidator();
                  //List<String> errors=  validator.validate(requestBodyJson, types.get(0));
                 // errors.forEach(e->{System.out.println(e);});
                  validateRequestBody(requestBodyJson, types.get(0), bindingResult);

        	   
            }
        
	        //Resource jsonSchemaResource = jsonSchemaResolver.resolveJsonSchemaResource(parameter, webRequest);
	        
	        //if (bindingResult.getErrorCount() > beforeSchemaValidationErrorCount && throwExceptionOnSchemaValidationError) {
	        //    throw new JsonSchemaValidationException(bindingResult);
	        //}
	    }
	    private void validateRequestBody(String json, Class<?> clazz, BindingResult bindingResult){
	        try {
	        	 
	        	SchemaValidator validator=new SchemaValidator();
	        	 List<String> errorList=  validator.validate(json, clazz);
                 if(!errorList.isEmpty()) {
                	 validationExceptionHandler.convert(errorList, bindingResult);
                 }
	             
	        } catch (JsonParseException e1) {
//				   e1.printStackTrace();
			} catch (IOException e1) {
//				e1.printStackTrace();
			}  
	    }
	   

	    private String getJsonPayload(NativeWebRequest webRequest) throws IOException {
	     	RequestWrapper httpServletRequest = webRequest.getNativeRequest(RequestWrapper.class);
	        return httpServletRequest.getBody();
	     }
	    
	    /**
	     * Represents an internal binding result mapped
	     * over an existing HTTP request with JSON body.
	     */
	    private static class WebRequestBindingResult extends AbstractBindingResult {

	 		private static final long serialVersionUID = 6515493608819078282L;
			private final WebRequest webRequest;

	        protected WebRequestBindingResult(WebRequest webRequest) {
	            super("request");
	            this.webRequest = webRequest;
	        }

	        @Override
	        public WebRequest getTarget() {
	            return this.webRequest;
	        }

	        @Override
	        protected Object getActualFieldValue(String field) {
	            return this.webRequest.getParameter(field);
	        }
	    }

}
