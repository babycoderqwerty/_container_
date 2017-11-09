package main.java.com.mmi.model;

public class ExeuQuery {
	
	private String table_name;
	private String field_name;
	private String where_clauses;
	
	
	public ExeuQuery() {
		
	}
	
	public ExeuQuery(String table_name, String field_name, String where_clauses) {
		super();
		this.table_name = table_name;
		this.field_name = field_name;
		this.where_clauses = where_clauses;
	}

	
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	
	public String getWhere_clauses() {
		return where_clauses;
	}
	public void setWhere_clauses(String where_clauses) {
		this.where_clauses = where_clauses;
	}

}
