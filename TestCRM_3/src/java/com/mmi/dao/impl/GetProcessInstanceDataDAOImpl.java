/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmi.dao.impl;

import com.google.gson.Gson;
import com.mmi.connection.DbConn;
import com.mmi.dao.GetProcessInstanceDataDAO;
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
public class GetProcessInstanceDataDAOImpl implements GetProcessInstanceDataDAO 
{

    @Override
    public String getProcessInstanceData(String pinst_id, String columns) 
    {
        SessionFactory sf = DbConn.getSessionFactory();
        Session session = sf.openSession();

        String jsonProcessInstanceData = null;

        Gson g = new Gson();
//            String jsonProcessInstanceData = null;
        System.out.println("Columns received");
        System.out.println(columns);

            //conversion : String data ----- > Map
        StringBuilder columnListSB = new StringBuilder();

        List<String> columnsList = g.fromJson(columns, ArrayList.class);

        String delimeter = "";
        for (String column : columnsList) {
            columnListSB.append(delimeter);
            delimeter = ",";
            columnListSB.append(column);
        }

        System.out.println("columns to be fetched ----- > ");
        System.out.println(columnListSB);

        String query = "select "
                + columnListSB
                + " from "
                + "process_ext "
                + "where "
                + "pinst_id = '" + pinst_id + "'";

        System.out.println("Query is  ------ > "+query);
        
        List<Object[]> pinst_data = session.createNativeQuery(query).getResultList();

        
        
//        System.out.println("Row ---- > ");

                // Preparing HashMap
        List<Map> finalDataList = new ArrayList<Map>();

        Map headers = new LinkedHashMap();
        Map processInstanceData = new LinkedHashMap();

        headers.put("header", columnsList);

        finalDataList.add(headers);

        List dataList = new ArrayList();

        for (Object[] o : pinst_data) {
            int counter = 0;
            Map finalData = new LinkedHashMap();

            for (Object column : columnsList) 
            {
                System.out.println(column.toString()+" : "+o[counter]);
                finalData.put(column.toString(), o[counter] == null ? "" : o[counter]);
                counter++;
            }

            dataList.add(finalData);
        }

        processInstanceData.put("data", dataList);

        finalDataList.add(processInstanceData);

        jsonProcessInstanceData = g.toJson(finalDataList);

        session.close();

        return jsonProcessInstanceData;
        
    }

}
