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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attendants == null) ? 0 : attendants.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Task other = (Task) obj;
		if (attendants == null) {
			if (other.attendants != null)
				return false;
		} else if (!attendants.equals(other.attendants))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
