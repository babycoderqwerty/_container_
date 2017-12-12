package com.mmi.model;

/**
 * @author Braj
 *
 */

public class RuTskModel {
	
	private int taskID;
	private String url;
	private String taskOwner;
	private String taskAssign;
	private String taskDelegationState;
	private String taskName;
	private String taskDescription;
	private String taskCreationTime;
	private String taskDueDate;
	private int taskPriority;
	private String taskSuspended;
	private String taskDefinitionKey;
	private String taskTenantId;
	private String taskCategory;
	private String taskFormKey;
	private String taskParentTaskId;
	private String taskParentTaskUrl;
	private String taskExecutionId;
	private String taskExecutionUrl;
	private int processInstanceId;
	private String processInstanceUrl;
	private String processDefinitionId;
	private String processDefinitionUrl;
	private String variables;
	
	
	public RuTskModel() {
		
	}
	
	public RuTskModel(int taskID, String url, String taskOwner, String taskAssign, String taskDelegationState,
			String taskName, String taskDescription, String taskCreationTime, String taskDueDate, int taskPriority,
			String taskSuspended, String taskDefinitionKey, String taskTenantId, String taskCategory,
			String taskFormKey, String taskParentTaskId, String taskParentTaskUrl, String taskExecutionId,
			String taskExecutionUrl, int processInstanceId, String processInstanceUrl, String processDefinitionId,
			String processDefinitionUrl, String variables) {
		
			super();
			this.taskID = taskID;
			this.url = url;
			this.taskOwner = taskOwner;
			this.taskAssign = taskAssign;
			this.taskDelegationState = taskDelegationState;
			this.taskName = taskName;
			this.taskDescription = taskDescription;
			this.taskCreationTime = taskCreationTime;
			this.taskDueDate = taskDueDate;
			this.taskPriority = taskPriority;
			this.taskSuspended = taskSuspended;
			this.taskDefinitionKey = taskDefinitionKey;
			this.taskTenantId = taskTenantId;
			this.taskCategory = taskCategory;
			this.taskFormKey = taskFormKey;
			this.taskParentTaskId = taskParentTaskId;
			this.taskParentTaskUrl = taskParentTaskUrl;
			this.taskExecutionId = taskExecutionId;
			this.taskExecutionUrl = taskExecutionUrl;
			this.processInstanceId = processInstanceId;
			this.processInstanceUrl = processInstanceUrl;
			this.processDefinitionId = processDefinitionId;
			this.processDefinitionUrl = processDefinitionUrl;
			this.variables = variables;
	}
	
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTaskOwner() {
		return taskOwner;
	}
	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}
	
	public String getTaskAssign() {
		return taskAssign;
	}
	public void setTaskAssign(String taskAssign) {
		this.taskAssign = taskAssign;
	}
	
	public String getTaskDelegationState() {
		return taskDelegationState;
	}
	public void setTaskDelegationState(String taskDelegationState) {
		this.taskDelegationState = taskDelegationState;
	}
	
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	public String getTaskCreationTime() {
		return taskCreationTime;
	}
	public void setTaskCreationTime(String taskCreationTime) {
		this.taskCreationTime = taskCreationTime;
	}
	
	public String getTaskDueDate() {
		return taskDueDate;
	}
	public void setTaskDueDate(String taskDueDate) {
		this.taskDueDate = taskDueDate;
	}
	
	public int getTaskPriority() {
		return taskPriority;
	}
	public void setTaskPriority(int taskPriority) {
		this.taskPriority = taskPriority;
	}
	
	public String getTaskSuspended() {
		return taskSuspended;
	}
	public void setTaskSuspended(String taskSuspended) {
		this.taskSuspended = taskSuspended;
	}
	
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	
	public String getTaskTenantId() {
		return taskTenantId;
	}
	public void setTaskTenantId(String taskTenantId) {
		this.taskTenantId = taskTenantId;
	}
	
	public String getTaskCategory() {
		return taskCategory;
	}
	public void setTaskCategory(String taskCategory) {
		this.taskCategory = taskCategory;
	}
	
	public String getTaskFormKey() {
		return taskFormKey;
	}
	public void setTaskFormKey(String taskFormKey) {
		this.taskFormKey = taskFormKey;
	}
	
	public String getTaskParentTaskId() {
		return taskParentTaskId;
	}
	public void setTaskParentTaskId(String taskParentTaskId) {
		this.taskParentTaskId = taskParentTaskId;
	}
	
	public String getTaskParentTaskUrl() {
		return taskParentTaskUrl;
	}
	public void setTaskParentTaskUrl(String taskParentTaskUrl) {
		this.taskParentTaskUrl = taskParentTaskUrl;
	}
	
	public String getTaskExecutionId() {
		return taskExecutionId;
	}
	public void setTaskExecutionId(String taskExecutionId) {
		this.taskExecutionId = taskExecutionId;
	}
	
	public String getTaskExecutionUrl() {
		return taskExecutionUrl;
	}
	public void setTaskExecutionUrl(String taskExecutionUrl) {
		this.taskExecutionUrl = taskExecutionUrl;
	}
	
	public int getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(int processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	
	public String getProcessInstanceUrl() {
		return processInstanceUrl;
	}
	public void setProcessInstanceUrl(String processInstanceUrl) {
		this.processInstanceUrl = processInstanceUrl;
	}
	
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	
	public String getProcessDefinitionUrl() {
		return processDefinitionUrl;
	}
	public void setProcessDefinitionUrl(String processDefinitionUrl) {
		this.processDefinitionUrl = processDefinitionUrl;
	}
	
	public String getVariables() {
		return variables;
	}
	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	

}
