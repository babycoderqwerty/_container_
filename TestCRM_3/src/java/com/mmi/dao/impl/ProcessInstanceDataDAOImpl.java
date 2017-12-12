/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmi.dao.impl;

import com.google.gson.Gson;
import com.mmi.connection.DbConn;
import com.mmi.dao.ProcessInstanceTableDataDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author CEINFO
 */
public class ProcessInstanceDataDAOImpl implements ProcessInstanceTableDataDAO {
    
    @Override
    public String getActColList(String activity_name) 
        {
            Gson g = new Gson();
            String jsonProcessInstanceData = null;
            String delimeter = "";
            StringBuilder strColumns = new StringBuilder("");
            List act_col_list = new ArrayList();
                    
            SessionFactory sf = DbConn.getSessionFactory();
            
            Session session = sf.openSession();
            Transaction tx = null;
            
            String sql = "select "
                    + "ext_column "
                    + "from "
                    + "proc_re_actcolmapping "
                    + "where "
                    + "upper(activity_key) = upper('"+activity_name+"') and enable = 'Y' "
                    + "order by sort_order asc";
            
            System.out.println("SQL to fetch mapped columns to "+activity_name);
            System.out.println(sql);
            
            try 
            {
                tx = session.beginTransaction();
//                act_col_list = session.createQuery(sql).list();
                
                act_col_list = session.createNativeQuery(sql).list();
                
                System.out.println("act_col_list ---- > ");
                System.out.println(act_col_list.toString());
                
                //iterating over list and preparing query to pull data from external table
                for (Object column : act_col_list)
                {
                    strColumns.append(delimeter);
                    delimeter = ",";
                    strColumns = strColumns.append(new StringBuilder(column.toString()));
                }
                
                System.out.println("Mapped columns");
                System.out.println(strColumns);
                // preparing to fetch data from external table
                
                String data = "select "
                    + strColumns
                    + " from "
                    + "process_ext "
//                    + "where "
//                    + "current_activity = '"+activity_name+"'"

                    + "limit 1";
                
                System.out.println("Data query ----- > ");
                System.out.println(data);
                
                List<Object[]> pinst_data = session.createNativeQuery(data).getResultList();
                
                System.out.println("Row ---- > ");
                
                // Preparing HashMap
                
                List<Map> finalDataList = new ArrayList<Map>();
                
                Map headers = new LinkedHashMap();
                Map processInstanceData = new LinkedHashMap();
                
                headers.put("header",act_col_list);
                
                finalDataList.add(headers);
                
                List dataList = new ArrayList();
                
                for (Object[] o : pinst_data)
                {
                    int counter = 0;
                    Map finalData = new LinkedHashMap();
                    
                    for (Object column : act_col_list)
                    {
//                        System.out.println("Column ---- >");
//                        System.out.println(column);
//                        System.out.println("Data ---- >");
//                        System.out.println(o[counter]);
                        finalData.put(column.toString(),o[counter]==null?"":o[counter]);
                        counter++;
                    }
                    
                    dataList.add(finalData);
                }
                
                processInstanceData.put("data",dataList);
                
                finalDataList.add(processInstanceData);
                
                jsonProcessInstanceData = g.toJson(finalDataList);
                
            } 
            catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            
            return jsonProcessInstanceData;            

    }
    
}
