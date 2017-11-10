package com.mmi.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mmi.dao.ProcessDAO;
import com.mmi.model.ProcessDefList;
import com.mmi.model.RuTskModel;
import com.mmi.model.UserModel;


@RestController

public class ProcessController {
	
	@Autowired
	ProcessDAO processDAO;
	
	@GetMapping("processlist/{call_Id}/repository")
	public List<ProcessDefList> getProDefList(@PathVariable("call_Id") String method_call) {
		List<ProcessDefList> pro_def_list = processDAO.getProDefList(method_call);
		return pro_def_list;
		
	}
	
	@GetMapping("process-definition/{method_call}/repository/{processDefinitionId}")
	public ProcessDefList getSingleProDef(@PathVariable("method_call") String method_call, @PathVariable("processDefinitionId") String processDefinitionId) {
		
		return processDAO.getSingleProDef(method_call, processDefinitionId);
		
	}

}
