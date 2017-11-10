
package com.mmi.dao;

import com.mmi.model.ProcessDefList;
import com.mmi.model.RuTskModel;
import com.mmi.model.UserModel;
import java.util.List;


public interface ProcessDAO 
{
	public List<ProcessDefList> getProDefList(String call_Id);
	
	public ProcessDefList getSingleProDef(String method_call, String processDefinitionId);
}
