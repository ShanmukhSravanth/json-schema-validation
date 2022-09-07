package json;

import java.io.IOException;
import java.util.List; 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.json.SchemaValidator;

import json.vo.Address;
import json.vo.Employee; 
public class TestNestedParse2 {

	public static void main(String[] args) throws IOException {
			Employee emp = new Employee();
				
				emp.setId(1);
				emp.setName("bharavi");
				emp.setPermanent(true); 
				Address address = new Address();
				address.setStreet("street 3");
				address.setCity("secundrabad");
				address.setZipcode("500010");
				emp.setAddress(address);
			
				ObjectMapper mapper=new ObjectMapper();
			    String json =	mapper.writeValueAsString(emp);
			    System.out.println(json);	
			    
			    List<String> errors= new SchemaValidator().validate(json, Employee.class);
			    errors.forEach(e->{System.out.println(e);});
			    
	}
	
	
	
	
}
