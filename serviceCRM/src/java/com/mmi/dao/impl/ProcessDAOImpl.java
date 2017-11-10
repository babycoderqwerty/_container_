package com.mmi.dao.impl;

import com.mmi.connection.DbConn;
import com.mmi.dao.ProcessDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.mmi.model.ExeuQuery;
import com.mmi.model.ProcessDefList;
import com.mmi.model.RuTskModel;
import com.mmi.model.UserModel;
import java.util.Iterator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class ProcessDAOImpl implements ProcessDAO {
	
	String select_query = "SELECT ";
	String from_query = " FROM ";
	String where_query = " WHERE ";
	
//	@Autowired
	private DataSource dataSource;
	
	/*Start of Get the list of all process definition*/
	@Override
//	public List<ProcessDefList> getProDefList(String call_Id) 
	public List<ProcessDefList> getProDefList(String call_Id) 
        {
            SessionFactory sf = DbConn.getSessionFactory();
            
            Session session = sf.openSession();
            Transaction tx = null;
            
            String sql = "select "
                    + "call_id, process, table_name, column_name, condition "
                    + "from "
                    + "act_re_extension "
                    + "where "
                    + "call_Id = '"+call_Id+"' and status = 'E'";
            
            try 
            {
                tx = session.beginTransaction();
                List<String> callIdDetails = session.createQuery(sql).list();
                
                System.out.println("Call id details ---- > ");
                System.out.println(callIdDetails.toString());
                /*
                for (Str = callIdDetails.iterator(); itr.hasNext();) 
                {
                    Employee employee = (Employee) itr.next();
                    System.out.print("First Name: " + employee.getFirstName());
                    System.out.print("  Last Name: " + employee.getLastName());
                    System.out.println("  Salary: " + employee.getSalary());
                }
                tx.commit();
                
                JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		
		ExeuQuery exeu_query = new ExeuQuery();
		jdbcTemplate.query(sql, new ResultSetExtractor<ExeuQuery>() {

			@Override
			public ExeuQuery extractData(ResultSet rs) throws SQLException, DataAccessException {
				while(rs.next()) {
					exeu_query.setTable_name(rs.getString("table_name"));
				}
				return null;
			}
		});
		
		String new_sql = select_query.concat("*").concat(from_query).concat(exeu_query.getTable_name());
		List<ProcessDefList> listContact = jdbcTemplate.query(new_sql, new RowMapper<ProcessDefList>() {

			@Override
			public ProcessDefList mapRow(ResultSet new_rs, int rowNum) throws SQLException {
				
				ProcessDefList process_def_list = new ProcessDefList();
				
//				process_def_list.setVersion(new_rs.getInt("version_"));
				process_def_list.setName(new_rs.getString("name_"));
//				process_def_list.setDescription(new_rs.getString("description_"));
//				
				return process_def_list;
			}

		});
		return listContact;
                */
                
            } 
            catch (HibernateException e) {
                if (tx != null) {
                    tx.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
            }
            
            return null;            

    }

        		
   
	/*END of Get the list of all process definition*/

	/*Start of Get Single Process definition List*/
	@Override
	public ProcessDefList getSingleProDef(String method_call, String processDefinitionId) 
        {
		
		String process_def_list = processDefinitionId;
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "select * from act_query_execution where method = '"+method_call+"' ";
		
		ExeuQuery exeu_query = new ExeuQuery();
		
		jdbcTemplate.query(sql, new ResultSetExtractor<ExeuQuery>() {

			@Override
			public ExeuQuery extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				while(rs.next()) {
					exeu_query.setTable_name(rs.getString("table_name"));
					exeu_query.setWhere_clauses(rs.getString("where_clauses"));
				} 
				return null;
			}
			
		});
		
		System.out.println(exeu_query.getTable_name());
		//String new_sql = select_query.concat("*").concat(from_query).concat(where_query).concat(clauses).concat("= "+"'"+processDefinitionId+"'");
		String new_sql = select_query.concat("*").concat(from_query).concat(exeu_query.getTable_name()).concat(where_query).concat(exeu_query.getWhere_clauses()).concat("= "+"'"+processDefinitionId+"'");
		ProcessDefList process_def = new ProcessDefList();
		
		jdbcTemplate.query(new_sql, new RowMapper<ProcessDefList>() {

			@Override
			public ProcessDefList mapRow(ResultSet new_rs, int rowNum) throws SQLException {
				
				process_def.setId(new_rs.getString("id_"));
				//process_def_list.setUrl(new_rs.getString(""));
				process_def.setKey(new_rs.getString("key_"));
				process_def.setVersion(new_rs.getInt("version_"));
				process_def.setName(new_rs.getString("name_"));
				process_def.setDescription(new_rs.getString("description_"));
				process_def.setTenantId(new_rs.getString("tenant_id_"));
				process_def.setDeploymentId(new_rs.getString("deployment_id_"));
				//process_def_list.setDeploymentUrl(new_rs.getString(""));
				process_def.setResource(new_rs.getString("resource_name_"));
				process_def.setDiagramResource(new_rs.getString("dgrm_resource_name_"));
				process_def.setCategory(new_rs.getString("category_"));
				process_def.setGraphicalNotationDefined(new_rs.getString("has_graphical_notation_"));
				process_def.setStartFormDefined(new_rs.getString("has_start_form_key_"));
				
				return process_def;
			}

		});
		return process_def;
		
		
	}
	
}
