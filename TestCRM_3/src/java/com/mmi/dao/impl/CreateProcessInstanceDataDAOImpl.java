/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmi.dao.impl;

import com.google.gson.Gson;
import com.mmi.connection.DbConn;
import com.mmi.dao.CreateProcessInstanceDataDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author CEINFO
 */
public class CreateProcessInstanceDataDAOImpl implements CreateProcessInstanceDataDAO 
{

    @Override
    public StringBuilder createProcessInstance() 
    {
        StringBuilder pinst_id = new StringBuilder("");
        StringBuilder pinst_prefix = new StringBuilder("MMI");// to be fetched dynamically
        StringBuilder pinst_suffix = new StringBuilder("CRM");// to be fetched dynamically
        
        int pinst_length = 10;// to be fetched dynamically
        int startcount = 2;// to be fetched dynamically

        StringBuilder unique_pinst_id = new StringBuilder(String.format("%0"+pinst_length+"d", startcount));
        
        pinst_id = pinst_prefix.append("-").append(unique_pinst_id).append("-").append(pinst_suffix);

        System.out.println("process instance created ----- > "+pinst_id);
        
        return pinst_id;
        
    }

}
