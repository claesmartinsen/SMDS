package taskObjects;

import java.io.Serializable;

public class ClientCall implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String functionName;
	private String parameter;
	
	public ClientCall(String methodName, String parameter){
		this.functionName = methodName;
		this.parameter = parameter;
	}
	
	public String getFunctionName(){
		return this.functionName;
	}
	
	public String getParameter(){
		return this.parameter;
	}
	
	
}
