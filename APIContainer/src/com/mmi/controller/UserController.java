package main.java.com.mmi.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import main.java.com.mmi.dao.UserDAO;
import main.java.com.mmi.model.ProcessDefList;
import main.java.com.mmi.model.RuTskModel;
import main.java.com.mmi.model.UserModel;


@RestController
public class UserController {
	
	@Autowired
	UserDAO userDAO;
	
	@GetMapping("{call_Id}/repository/process-definition")
	public List<ProcessDefList> getProDefList(@PathVariable("call_Id") String method_call) {
		List<ProcessDefList> pro_def_list = userDAO.getProDefList(method_call);
		return pro_def_list;
		
	}
	
	@GetMapping("{method_call}/repository/process-definition/{processDefinitionId}")
	public ProcessDefList getSingleProDef(@PathVariable("method_call") String method_call, @PathVariable("processDefinitionId") String processDefinitionId) {
		
		return userDAO.getSingleProDef(method_call, processDefinitionId);
		
	}
	
	@GetMapping("users/{process_id}/{user_id}")
	public UserModel getUser(@PathVariable("process_id") String process_id, @PathVariable("user_id") String user_id) {
		/*userDAO.getUserRole(process_id, user_id);*/
		return userDAO.getUserRole(process_id, user_id);
		
	}
	
	@GetMapping("{process_id}/{method_call}/runtime/tasks")
	public RuTskModel getRuTskList(@PathVariable("process_id") String process_id, @PathVariable("method_call") String method_call) {
		
		return userDAO.RuTskList(process_id, method_call);
		
	}
	

	

}
