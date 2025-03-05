package com.cafein.ServiceImpl;

import java.util.Map;
import java.util.Objects;

import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.cafein.Utils.CafeUtils;
import com.cafein.POJO.User;
import com.cafein.Constants.CafeConstants;
import com.cafein.Dao.UserDao;
import com.cafein.Service.UserService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserDao userDao;
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
	//	Log.info("Inside signup {}",requestMap);
		try {
		if(validateSignUpMap(requestMap)) {
			User user=userDao.findByEmailId(requestMap.get("email"));
			if(Objects.isNull(user)) {
				userDao.save(getUserFromMap( requestMap));
				return CafeUtils.getResponseEnity("Successfully Registered", HttpStatus.OK);
			}
			else {
				return CafeUtils.getResponseEnity("Email already exist",HttpStatus.BAD_REQUEST);
			}
		}
		else {
			return CafeUtils.getResponseEnity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
		
		return CafeUtils.getResponseEnity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private boolean validateSignUpMap(Map<String,String> requestMap) {
		if(	requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
			&& requestMap.containsKey("email") && requestMap.containsKey("password")) {
			return true;
			
		}
		else {
			return false;
		}

     }
	
	
	private User getUserFromMap(Map<String,String> requestMap) {
		User user =new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber( requestMap.get("contactNumber"));
		user.setEmail( requestMap.get("email"));
		user.setPassword( requestMap.get("password"));
		user.setStatus("false");
		user.setRole("user");
		return user;
	}
}
