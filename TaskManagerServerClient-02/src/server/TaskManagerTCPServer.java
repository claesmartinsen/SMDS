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

public class TaskManagerTCPServer {

	private static String xmlPath = "/task-manager-xml.xml";

	public static void main(String args[]) {
		System.out.println("path is: " + xmlPath);
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
			ObjectInputStream ois = new ObjectInputStream(is);
			
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			 
			//Note that read calls also blocking and wont return until we have some data sent by client. 
			ClientCall cc = (ClientCall) ois.readObject(); // blocking call
			outputStream.writeObject(cc); //Send back the client call for verification in the client.

			switch(cc.getFunctionName())
			{
			case GET: 
				// Now the server switches to output mode delivering some message to client.
				outputStream.writeObject(new ServerResponse("Success", (Serializable) get(cc.getParameter())));
				outputStream.flush();
				break;
			case PUT:
				put(cc.getTask());
				// Now the server switches to output mode delivering some message to client.
				outputStream.writeObject(new ServerResponse("Success", null));
				outputStream.flush();
				break;
			case DELETE:
				delete(cc.getParameter());
				outputStream.writeObject(new ServerResponse("success", null));
				outputStream.flush();
				break;
			case POST:
				post(cc.getTask());
				outputStream.writeObject(new ServerResponse("success", null));
				outputStream.flush();
				break;
			} 
			socket.close();
			serverSocket.close();
			

		} catch (IOException ex) {
			Logger.getLogger(TaskManagerTCPServer.class.getName()).log(Level.SEVERE, null, ex);

			System.out.println("error message: " + ex.getMessage());
		}
	}

	public static void post(Task task){
		try {
			Cal cal = getCal();

			ListIterator<Task> allTasks = cal.tasks.listIterator();
			while(allTasks.hasNext())
				if(allTasks.next().id.equals(task.id)) {
					allTasks.remove();
				}
			cal.tasks.add(task);
			saveCal(cal);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void delete(String id) {
		
		try{
			System.out.println("delete invoked");
			Cal cal = getCal();
			List<Task> taskList = new ArrayList<Task>(); 

			for(Task t : cal.tasks){
				if(!t.id.equals(id)) {
					taskList.add(t);
				}
			}

			cal.tasks = taskList;
			saveCal(cal);
		} catch (JAXBException e){
			e.printStackTrace();
		}
		
	}

	public static void put(Task task){
		try {
			Cal cal = getCal();
			cal.tasks.add(task);
			saveCal(cal);	
		} catch (JAXBException e) {

			e.printStackTrace();
		}
	}

	public static List<Task> get(String attendant) throws IOException
	{
		List<Task> taskList = getAllTasks();
		List<Task> filteredTasks = new ArrayList<Task>();
		for(Task t : taskList){
			if(t.attendants.contains(attendant)) filteredTasks.add(t);
		}
		return filteredTasks;
	}

	private static List<Task> getAllTasks(){
		try {
			Cal cal = getCal();
			List<Task> allTasks = cal.tasks;
			return allTasks;
		} catch (JAXBException e) {
			System.out.println("getAll method couldn+t proceed" + "\n");
			e.printStackTrace();
			return null;
		}
	}

	private static Cal getCal() throws JAXBException{
		System.out.println("GET invoked");    
		Object obj = new Object();

		System.out.println("Reading XML");
		InputStream stream = obj.getClass().getResourceAsStream(xmlPath);

		// create an instance context class, to serialize/deserialize.
		JAXBContext jaxbContext = JAXBContext.newInstance(Cal.class);

		// Deserialize task xml into java objects.
		Cal cal = (Cal) jaxbContext.createUnmarshaller().unmarshal(stream);
		return cal;
	}

	private static void saveCal(Cal cal) throws JAXBException{
		System.out.println("GET invoked");    
		Object obj = new Object();

		// Get path to the university-xml.xml using relative path.
		String path = obj.getClass().getResource(xmlPath).getPath();

		// create an instance context class, to serialize/deserialize.
		JAXBContext jaxbContext = JAXBContext.newInstance(Cal.class);
		// Serialize university object into xml.

		StringWriter writer = new StringWriter();

		//serialize or deserialize University class.
		jaxbContext.createMarshaller().marshal(cal, writer);

		// Finally save the Xml back to the file.
		try {
			SaveFile(writer.toString(), path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void SaveFile(String xml, String path) throws IOException {
		File file = new File(path);
		System.out.println("The path is: " + path);
		BufferedWriter output = new BufferedWriter(new FileWriter(file)); 		// create a bufferedwriter to write Xml
		output.write(xml);
		output.close();
	}
}