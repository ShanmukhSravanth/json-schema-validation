package json;
 
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.json.SchemaValidator;

import json.vo.StudentRegistration;

public class JsonSchemaTest {

	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
	 
		ObjectMapper mapper = new ObjectMapper();
		//JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
		//JsonSchema schema = schemaGen.generateSchema(StudentRegistration.class);
		
		//String dd=mapper.writeValueAsString(schema);
		//System.out.println("Schema:"+dd);
		
		  
		
		String  js = "{"
				//+ "\"id\":\"1\","
				+ "\"name\":\"bharavi\","
				+ "\"age\":\"0\","
				+ "\"name\":\"bharavi2\","
				+ "\"name2\":\"bharavi2\""
				+ "}";
		System.out.println(js);
	
		 
		 
		  
	    List<String> errors= new SchemaValidator().validate(js, StudentRegistration.class);
	    errors.forEach(e->{System.out.println(e);});
	   
	    String  js2 = "{\"id\":\"1\",\"name\":\"bharavi\",\"name2\":\"0\",\"name\":\"bharavi2\"}";
	    System.out.println(js2);
		
		
	    errors= new SchemaValidator().validate(js2, StudentRegistration.class);
	    errors.forEach(e->{System.out.println(e);});
		

	}
}
