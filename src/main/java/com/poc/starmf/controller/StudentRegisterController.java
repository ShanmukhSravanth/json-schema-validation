package com.poc.starmf.controller;
import java.util.List;

import org.springframework.stereotype.Controller; 
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poc.json.schema.annotation.JsonRequestBody; 

@Controller
public class StudentRegisterController {

 @RequestMapping(method = RequestMethod.GET, value="/")
 public  String welcome( ) {
	   
	   return "login";
 }	
 
 @RequestMapping(value = "/login", method = RequestMethod.POST)
 public @ResponseBody String login(@RequestParam("userid") String userid,@RequestParam("password") String password) {
		 
	return userid+":"+password;
 }

 @RequestMapping(method = RequestMethod.POST, value="/student")
 public @ResponseBody StudentRegistrationReply registerStudent(@JsonRequestBody StudentRegistration studentregd,Errors errors) {
    int count=errors.getErrorCount();
    System.out.println("Errors:"+count);
    List<ObjectError>  all=errors.getAllErrors();
    all.forEach(o->{
    	System.out.println(o.getCode()+":"+o.getDefaultMessage());
    });
	StudentRegistrationReply stdregreply = new StudentRegistrationReply(); 
    stdregreply.setName(studentregd.getName());
    stdregreply.setAge(studentregd.getAge());
    stdregreply.setRegistrationNumber("12345678");
    stdregreply.setRegistrationStatus("Successful");
    return stdregreply;

 }

}