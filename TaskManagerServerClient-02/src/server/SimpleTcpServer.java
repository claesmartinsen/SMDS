/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import taskObjects.Cal;
import taskObjects.ClientCall;
import taskObjects.ServerResponse;
import taskObjects.Task;


/**
 *
 * @author rao
 */
public class SimpleTcpServer {
	
    public static void main(String args[]) {
    	try {
			startServer();
		} catch (ClassNotFoundException e) {
			System.out.println("cannot start server " + e.getMessage());
			e.printStackTrace();
		}
    	
    }
 
    	
    private static void startServer() throws ClassNotFoundException{	
        try {
            int serverPort = 7896;
            ServerSocket serverSocket = new ServerSocket(serverPort); // create a server socket listening at port 7896
            System.out.println("Server started at 7896");
            Socket socket = serverSocket.accept(); // Server starts accepting requests. This is blocking call, and it wont return, until there is request from a client.

            InputStream is = socket.getInputStream(); // Get the inputstream to receive data sent by client.
            // based on the type of data we want to read, we will open suitbale input stream.  
            //TODO confirm clientcall
            
            
            ObjectInputStream ois = new ObjectInputStream(is);
            // Read the String data sent by client at once using readUTF,
            // Note that read calls also blocking and wont return until we have some data sent by client. 
            
            ClientCall cc = (ClientCall) ois.readObject(); // blocking call
        
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        
            switch(cc.getFunctionName())
            {
            case GET: 
            	// Now the server switches to output mode delivering some message to client.
            	outputStream.writeObject(new ServerResponse("Sucess", (Serializable) GET(cc.getParameter())));
            	System.out.println("GET was invoked");
            	outputStream.flush();
            	break;
            case PUT:
            	PUT(cc.getTask());
            	// Now the server switches to output mode delivering some message to client.
            	outputStream.writeObject(new ServerResponse("Sucess", null));
            	outputStream.flush();
            	break;
            case DELETE:
            	break;
            case POST:
            	break;
            
            } 
            
            	
            	
            socket.close();
            serverSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(SimpleTcpServer.class.getName()).log(Level.SEVERE, null, ex);

            System.out.println("error message: " + ex.getMessage());
        }

    }
    
    public static void PUT(Task task){
    	
    
    }

    public static List<Task> GET(String attendant) throws IOException
    {
    	try {

    		System.out.println("GET invoked");    
        	Object obj = new Object();
        	
        	
        	String xmlPath = "/task-manager-xml.xml";
        	// Get path to the university-xml.xml using relative path.
        	String path = obj.getClass().getResource(xmlPath).getPath();
        	
        	System.out.println("Reading XML");
        	InputStream stream = obj.getClass().getResourceAsStream(xmlPath);
        	
        	
            // create an instance context class, to serialize/deserialize.
            JAXBContext jaxbContext = JAXBContext.newInstance(Cal.class);

            // Create a file input stream for the university Xml.
            //FileInputStream stream = new FileInputStream(path);

            // Deserialize task xml into java objects.
            Cal cal = (Cal) jaxbContext.createUnmarshaller().unmarshal(stream);

            // Iterate through the collection of student object and print each student object in the form of Xml to console.
            ListIterator<Task> listIterator = cal.tasks.listIterator();
            
            List<Task> taskList = new ArrayList<Task>();
            while (listIterator.hasNext()) 
            {
            	Task t = listIterator.next();
            	if(t.attendants.contains(attendant))	taskList.add(t);
            }
     
         
            // Serialize university object into xml.
            
            StringWriter writer = new StringWriter();

            // We can use the same context object, as it knows how to 
            //serialize or deserialize University class.
            jaxbContext.createMarshaller().marshal(cal, writer);

            
            System.out.println("Printing serialized university Xml before saving into file!");
            
            // Print the serialized Xml to Console.
            //System.out.println(writer.toString());
            
            System.out.println("trying to save to xml");
            // Finally save the Xml back to the file.
            System.out.println("The path is: " + path);
            //SaveFile(writer.toString(), path);

            return taskList;

        } catch (JAXBException ex) {
        	System.out.println("something went wrong!!!");
            Logger.getLogger(SimpleTcpServer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    	
		
    

    }
    
    
    //Test
    @SuppressWarnings("unused")
	private static void PrintStudentObject(Task student) 
    {
        try {


            StringWriter writer = new StringWriter();

            // create a context object for Student Class
            JAXBContext jaxbContext = JAXBContext.newInstance(Task.class);

            // Call marshal method to serialize student object into Xml
            jaxbContext.createMarshaller().marshal(student, writer);

            System.out.println(writer.toString());

        } catch (JAXBException ex) {
            Logger.getLogger(SimpleTcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }




    }
    
    @SuppressWarnings("unused")
	private static void SaveFile(String xml, String path) throws IOException {


        File file = new File(path);
        
        System.out.println("The path is: " + path);
        // create a bufferedwriter to write Xml
        BufferedWriter output = new BufferedWriter(new FileWriter(file));

        output.write(xml);

        output.close();



    }

    
   
}