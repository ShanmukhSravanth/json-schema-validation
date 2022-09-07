package com.poc.json;

import java.io.IOException;
import java.lang.reflect.Field; 
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.json.JsonObject.Entry;
 
/*
 * Add support for configuration
 * mode= strict
 * allow unknown
 * allow missing
 * allow duplicate
 */
public class SchemaValidator {
 
	public SchemaValidator() {
		
	}
	public List<String> validate(String json,Class<?> clazz) throws JsonParseException, IOException{
		JsonObject element = getObject(json);
		return verifyElement(element,clazz);
	}
	private  JsonObject getObject(String json) throws JsonParseException, IOException {
		
	      
		ObjectMapper mapper=new ObjectMapper();
		JsonParser jp= mapper.getFactory().createParser(json);
        Stack<JsonObject> stack=new Stack<>();
         
         JsonToken nextToken = jp.nextToken();//Must be START_OBJECT
         /*
          * Declared out side to get the handle of main object
          */
         JsonObject root=new JsonObject();
         stack.push(root);
         
        while ((nextToken=jp.nextToken()) != null) {
        	
        	switch(nextToken) {
         
	        case END_OBJECT:
        		stack.pop();
        		break;
           
	         case FIELD_NAME:
        		
        		if((nextToken=jp.nextToken())==JsonToken.START_OBJECT) {
        			JsonObject obj= new JsonObject();
        			stack.peek().add(jp.currentName(), obj);
        			stack.push(obj);// make this as active objective
        			
        		}else {
        			stack.peek().add(jp.currentName(), new JsonElement());
        		}
        		break;	
	         default://do nothing
	        	
        	}
            
        }
        return root;
	}
   
	private  List<String> verifyElement(JsonObject element, Class<?> klass) {
	     
		List<String> errorFields = new ArrayList<>(); 
		  
		List<String> usedFields = new ArrayList<>();
		
		Map<String,Field> classFields = new HashMap<>();

		  for (Field field : klass.getDeclaredFields()) {
		    classFields.put(field.getName(),field);
		  }
	
		  for (Entry<String, JsonElement> entry : element.entrySet()) {
			  
	        if (!classFields.containsKey(entry.getKey())) {
		    	
		       if(usedFields.contains(entry.getKey())) {	
		    	   errorFields.add("duplicate:"+entry.getKey()); 
		       }else {
		    	   errorFields.add("unknown:"+entry.getKey()); 
		       }
		       
		    } else {
		      usedFields.add(entry.getKey());	
		      Field field=classFields.remove(entry.getKey());	
		      Class<?> fieldClass = field.getType();
		      if (!fieldClass.isPrimitive() && entry.getValue().isJsonObject()) {
		        List<String> elementErrors = verifyElement(entry.getValue().getAsJsonObject(), fieldClass);
		         errorFields.addAll(elementErrors);
		      }
		    }
		  }
		  classFields.keySet().forEach(key->{
//			  errorFields.add("missing:"+key);
		  });
		  
		  return errorFields;

		}
}
