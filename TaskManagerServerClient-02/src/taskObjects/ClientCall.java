package taskObjects;

import java.io.Serializable;

public class ClientCall implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FunctionName functionName;
	private String parameter;
	private Task task;
	
	public ClientCall(FunctionName functionName, String parameter){
		this.functionName = functionName;
		this.parameter = parameter;
	}
	
	public ClientCall(FunctionName functionName, String parameter, Task task){
		this.functionName = functionName;
		this.parameter = parameter;
		this.task = task;
	}
	
	public Task getTask(){
		if(task != null) return this.task;
		else return null;
	}
	
	public FunctionName getFunctionName(){
		return this.functionName;
	}
	
	public String getParameter(){
		return this.parameter;
	}
	
	
}
