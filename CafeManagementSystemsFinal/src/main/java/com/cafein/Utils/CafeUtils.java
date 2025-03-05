package com.cafein.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtils {

private CafeUtils() {
		
	}
	
	public static ResponseEntity<String> getResponseEnity(String responseMessage,HttpStatus httpStatus){
		return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}",httpStatus);
	}

}
