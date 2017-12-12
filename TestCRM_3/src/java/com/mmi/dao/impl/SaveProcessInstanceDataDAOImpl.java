/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmi.dao.impl;

import com.google.gson.Gson;
import com.mmi.connection.DbConn;
import com.mmi.dao.SaveProcessInstanceDataDAO;
import java.util.LinkedHashMap;
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
public class SaveProcessInstanceDataDAOImpl implements SaveProcessInstanceDataDAO {

    SessionFactory sf = DbConn.getSessionFactory();
    Gson g = new Gson();

    @Override
    public void saveProcessInstanceData(String data) {

        
        
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder("(");

        //conversion : String data ----- > Map
        Map<String, Object> processInstanceData = g.fromJson(data, LinkedHashMap.class);

        //  handling insert
        boolean isNew = processInstanceData.get("isNew").toString().equalsIgnoreCase("Y") ? true : false;

        try 
        {
            System.out.println("Is process instance new ?");
            System.out.println(isNew);
            if (isNew) 
            {
                String delimeter = "";
                for (Entry<String, Object> entry : ((Map<String, Object>) processInstanceData.get("values")).entrySet()) {
                    columns.append(delimeter);
                    values.append(delimeter);
                    delimeter = ",";
                    columns.append(new StringBuilder(entry.getKey()));
                    values = values.append("?");
                }
                
                if(columns.length() > 0)
                {
                    columns.append(",");
                    values.append(",");
                }
                columns.append("pinst_id");
                values.append("?");

                values.append(")");

                String insert = "insert into "
                        + "process_ext "
                        + "(" + columns + ") "
                        + "values "
                        + values;

                System.out.println("Insert data statement ----- > ");
                System.out.println(insert);

                Query saveData = session.createNativeQuery(insert);

                int counter = 1;

                for (Entry<String, Object> entry : ((Map<String, Object>) processInstanceData.get("values")).entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                    saveData.setParameter(counter, entry.getValue());
                    counter++;
                }

                saveData.setParameter(counter, processInstanceData.get("pinst_id"));

                saveData.executeUpdate();

                tx.commit();

            }
            else
            {
                String delimeter = "";
                StringBuilder updateData = new StringBuilder();
                
                StringBuilder whereClause = new StringBuilder();
                
                whereClause.append("pinst_id = ").append("'"+processInstanceData.get("pinst_id")+"'");
                
                for (Entry<String, Object> entry : ((Map<String, Object>) processInstanceData.get("values")).entrySet()) {
                    updateData.append(delimeter);
//                    values.append(delimeter);
                    delimeter = ",";
                    updateData.append(new StringBuilder(entry.getKey()));
                    updateData.append("=");
                    updateData.append("?");
                }


                String updateQuery = "update "
                        + "process_ext "
                        + "set "
                        + updateData
                        + " where "
                        + whereClause;

                System.out.println("update data statement ----- > ");
                System.out.println(updateQuery);

                Query saveData = session.createNativeQuery(updateQuery);

                int counter = 1;

                for (Entry<String, Object> entry : ((Map<String, Object>) processInstanceData.get("values")).entrySet()) {
                    System.out.println(entry.getKey() + " : " + entry.getValue());
                    saveData.setParameter(counter, entry.getValue());
                    counter++;
                }
                
                saveData.executeUpdate();

                tx.commit();
            }
        } 
//        catch (Exception e) 
//        {
//        } 
        
        finally 
        {
            if(session != null)
            {
                session.close();
            }
        }

    }

    //@Override
    public void saveProcessInstanceData(String pinst_id, String data) {
        SessionFactory sf = DbConn.getSessionFactory();
        Session session = sf.openSession();

        Transaction tx = session.beginTransaction();

//            String jsonProcessInstanceData = null;
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder("(");

        //conversion : String data ----- > Map
        Map<String, Object> processInstanceData = g.fromJson(data, LinkedHashMap.class);

        //  adding pinst_id to the map
        processInstanceData.put("pinst_id", pinst_id);

        System.out.println("Calling save data");

        //convert to JSON string
        String jsonData = g.toJson(processInstanceData);

        saveProcessInstanceData(jsonData.toString());

    }

}
