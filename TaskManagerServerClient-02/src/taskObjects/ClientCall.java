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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((functionName == null) ? 0 : functionName.hashCode());
		result = prime * result
				+ ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientCall other = (ClientCall) obj;
		if (functionName != other.functionName)
			return false;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		return true;
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
