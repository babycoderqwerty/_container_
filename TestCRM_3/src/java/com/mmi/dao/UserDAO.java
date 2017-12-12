package com.mmi.dao;

import java.util.List;

import com.mmi.model.ProcessDefList;
import com.mmi.model.RuTskModel;
import com.mmi.model.UserModel;

public interface UserDAO {
	
	
	
	public UserModel getUserRole(String processID, String userID);
	
	public RuTskModel RuTskList(String processID, String method_call);

}
