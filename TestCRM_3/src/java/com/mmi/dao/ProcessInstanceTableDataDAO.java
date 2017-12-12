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
public interface ProcessInstanceTableDataDAO {
    
    //	public List<ProcessDefList> getProDefList(String call_Id);
	public String getActColList(String activity_name);
    
}
