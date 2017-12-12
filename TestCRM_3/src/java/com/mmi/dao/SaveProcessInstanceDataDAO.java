/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmi.dao;

import java.util.List;

/**
 *
 * @author CEINFO
 */
public interface SaveProcessInstanceDataDAO {
    
    //	public List<ProcessDefList> getProDefList(String call_Id);
	public void saveProcessInstanceData( String data);
	public void saveProcessInstanceData(String pinst_id,  String data);
    
}
