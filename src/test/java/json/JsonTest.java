package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import json.vo.StudentRegistration;
import json.vo.StudentRegistrationReply;
 

public class JsonTest {

	public static void main(String[] args) throws RestClientException, URISyntaxException {
	 
       new JsonTest().test();
		
	}
	public void test2() throws RestClientException, URISyntaxException {
	    final String uri = "http://127.0.0.1:8080/student";
	     StudentRegistration reg=new StudentRegistration();
	     reg.setId("1");
	     reg.setAge(46);
	     reg.setName("Bharavi");
	   
	     
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<StudentRegistrationReply> result = restTemplate.postForEntity(new URI(uri),reg, StudentRegistrationReply.class);
	    System.out.println(result);
	    if(result.hasBody())
	    System.out.println(result.getBody().getRegistrationNumber());
	
	}

	public void test() {
	
	try {

        URL url = new URL("http://127.0.0.1:8080/student");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        String input = "{\"name\": \"bharavi\","
        		+ "\"age\":\"46\","
        		+ "\"age\":\"46\","
        		+ "\"age\":\"47\","
        		+ "\"name2\":\"name2\""
        		+ "}";

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            System.out.println(output);
        }

        conn.disconnect();

      } catch (MalformedURLException e) {

        e.printStackTrace();

      } catch (IOException e) {

        e.printStackTrace();

     }
}
	
	
}
