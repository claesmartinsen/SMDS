package taskObjects;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Serialization class for Student 
 * @author rao
 */

// Specify  XmlRootElement annotation, if you want to serailize a class into a standalone XML.

@XmlRootElement(name = "task")
public class Task implements Serializable {
    
    public Task(String id, String name, String date, String status,
			String description, String attendants) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.status = status;
		this.description = description;
		this.attendants = attendants;
	}
    
    public Task(){}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2534888777807503057L;

	@XmlAttribute
    public String id;
    
    @XmlAttribute
    public String name;
    
    // If you dont specify any annotation, it will be serialized as XmlElement.
    
    @XmlAttribute
    public String date;
    
    @XmlAttribute
    public String status;
    
    @XmlElement
    public String description;
    
    @XmlElement
    public String attendants;
    
    public String ToString(){
    	return "\n Attendants: " + attendants + 
    			"\n ID: " + id + 
    			"\n Name: " + name + 
    			"\n Description " + description +
    			"\n Date: " + date +
    			"\n Status: " + status
    			;
    	
    }
}
