package com.atro.oauth2server.contrller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
	
	@GetMapping("/")
	public String home(){
		
		return "helllo";
	}
	
	@GetMapping("/api")
	public String api(){
		
		return "Hello From API";
	}
	

}
