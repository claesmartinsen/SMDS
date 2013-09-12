/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taskObjects;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * Serialization class for TaskManager XML
 * @author 
 */
@XmlRootElement(name = "cal")
public class Cal {

    @XmlElementWrapper(name = "tasks")
    @XmlElement(name = "task")
    public List<Task> tasks;
    
    
    
    // A TaskManager may contain many other collections and entities  
    // but we don't condsider them here...
}
