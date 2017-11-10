package com.mmi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mmi.dao.UserDAO;

import com.mmi.model.RuTskModel;
import com.mmi.model.UserModel;


@RestController

public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	
	@GetMapping(value="users/{process_id}/{user_id}")
	public UserModel getUser(@PathVariable("process_id") String process_id, @PathVariable("user_id") String user_id) {
		/*userDAO.getUserRole(process_id, user_id);*/
		return userDAO.getUserRole(process_id, user_id);
		
	}
	
	@GetMapping(value="{process_id}/{method_call}/runtime/tasks")
	public RuTskModel getRuTskList(@PathVariable("process_id") String process_id, @PathVariable("method_call") String method_call) {
		
		return userDAO.RuTskList(process_id, method_call);
		
	}
	

	

}
