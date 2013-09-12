package taskObjects;

import java.io.Serializable;
import java.util.List;

public class ServerResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7060473969744649837L;
	/**
	 * 
	 */
	private String message;
	private Serializable object;
	
	public ServerResponse(String message, Serializable object){
		this.message = message;
		this.object = object;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public Serializable getObject(){
		return this.object;
	}
}
