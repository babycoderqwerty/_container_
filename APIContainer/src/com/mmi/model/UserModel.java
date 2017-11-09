package main.java.com.mmi.model;

/**
 * @author Braj
 *
 */

public class UserModel {
	
	private String userID;
	private String processID;
	private String userRole;
	private String userGroup;
	
	
	public UserModel() {
		
	}
	
	public UserModel(String userID, String userGroup, String processID, String userRole) {
		super();
		this.userID = userID;
		this.userGroup = userGroup;
		this.processID = processID;
		this.userRole = userRole;
	}

	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}

	public String getProcessID() {
		return processID;
	}

	public void setProcessID(String processID) {
		this.processID = processID;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	
	

}
