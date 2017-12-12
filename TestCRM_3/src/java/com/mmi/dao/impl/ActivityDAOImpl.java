package com.mmi.dao.impl;

import com.mmi.connection.DbConn;
import com.mmi.dao.UserDAO;
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

public class ActivityDAOImpl implements UserDAO {
	
	String select_query = "SELECT ";
	String from_query = " FROM ";
	String where_query = " WHERE ";
	
//	@Autowired
	private DataSource dataSource;
	

        /*Start of Get User Role List*/
	@Override
	public UserModel getUserRole(String proessID, String userID) {

		String process_id = proessID;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "select * from act_query_execution where process_id = '"+proessID+"'";
		UserModel user = new UserModel();
		jdbcTemplate.query(sql, new ResultSetExtractor<UserModel>() {

			@Override
			public UserModel extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				while (rs.next()) {
					String table_name = rs.getString(3);
					String column_name = rs.getString(4);
					String clauses = rs.getString(5);
					
					//String sql_get_role = "SELECT group_id_ FROM act_id_membership WHERE user_id_ = 'braj'";
					String sql_get_role = select_query.concat(clauses+",").concat(column_name).concat(from_query).concat(table_name).concat(where_query).concat(clauses).concat("= "+"'"+userID+"'");					
					
					jdbcTemplate.query(sql_get_role, new RowMapper<UserModel>() {

						@Override
						public UserModel mapRow(ResultSet new_rs, int rowNum) throws SQLException {
							// TODO Auto-generated method stub
							
							user.setProcessID(process_id);
							//user.setUserRole(new_rs.getString("group_id_"));
							user.setUserID(new_rs.getString("user_id_"));
							user.setUserGroup(new_rs.getString("group_id_"));
							return user;
						}});	
				}
				return null;
			}
		});
		
		 return user;
	}
	
	/*	End of Get User Role List*/

	/*Start of Get  run time task List*/
	@Override
	public RuTskModel RuTskList(String proessID, String method_call) {
		
		RuTskModel ru_tsk_list = new RuTskModel();
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "select * from act_query_execution WHERE process_id = '"+proessID+"' AND method = '"+method_call+"'";
		jdbcTemplate.query(sql, new ResultSetExtractor<RuTskModel>() {

			@Override
			public RuTskModel extractData(ResultSet rs) throws SQLException, DataAccessException {
				
				while(rs.next()) {
					
					System.out.println(rs.getString("table_name"));
					String sql_get_ru_list = select_query.concat("*").concat(from_query).concat(rs.getString("table_name"));
					jdbcTemplate.query(sql_get_ru_list, new RowMapper<RuTskModel>() {

						@Override
						public RuTskModel mapRow(ResultSet new_rs, int rowNum) throws SQLException {
							
							ru_tsk_list.setTaskID(new_rs.getInt("id_"));
							//ru_tsk_list.setUrl(new_rs.getString(""));
							ru_tsk_list.setTaskOwner(new_rs.getString("owner_"));
							ru_tsk_list.setTaskAssign(new_rs.getString("assignee_"));
							ru_tsk_list.setTaskDelegationState(new_rs.getString("delegation_"));
							ru_tsk_list.setTaskName(new_rs.getString("name_"));
							ru_tsk_list.setTaskDescription(new_rs.getString("description_"));
							ru_tsk_list.setTaskCreationTime(new_rs.getString("create_time_"));
							ru_tsk_list.setTaskDueDate(new_rs.getString("due_date_"));
							ru_tsk_list.setTaskPriority(new_rs.getInt("priority_"));
							ru_tsk_list.setTaskSuspended(new_rs.getString("suspension_state_"));
							ru_tsk_list.setTaskDefinitionKey(new_rs.getString("task_def_key_"));
							ru_tsk_list.setTaskTenantId(new_rs.getString("tenant_id_"));
							ru_tsk_list.setTaskCategory(new_rs.getString("category_"));
							ru_tsk_list.setTaskFormKey(new_rs.getString("form_key_"));
							ru_tsk_list.setTaskParentTaskId(new_rs.getString("parent_task_id_"));
							//ru_tsk_list.setTaskParentTaskUrl(new_rs.getString(""));
							ru_tsk_list.setTaskExecutionId(new_rs.getString("execution_id_"));
							//ru_tsk_list.setTaskExecutionUrl(new_rs.getString(""));
							ru_tsk_list.setProcessInstanceId(new_rs.getInt("proc_inst_id_"));
							//ru_tsk_list.setProcessInstanceUrl(new_rs.getString(""));
							ru_tsk_list.setProcessDefinitionId(new_rs.getString("proc_def_id_"));
							//ru_tsk_list.setProcessDefinitionUrl(new_rs.getString(""));
							//ru_tsk_list.setVariables(new_rs.getString(""));
							return ru_tsk_list;
						}
						
					});
					
				}
				return null;
			}
			
		});
		
		return ru_tsk_list;
	}
	
	/*END of Get  run time task List*/
	
	
}
