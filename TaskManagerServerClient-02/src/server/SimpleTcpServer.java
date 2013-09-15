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

public class SimpleTcpServer {

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
				outputStream.writeObject(new ServerResponse("Sucess", (Serializable) get(cc.getParameter())));
				outputStream.flush();
				break;
			case PUT:
				put(cc.getTask());
				// Now the server switches to output mode delivering some message to client.
				outputStream.writeObject(new ServerResponse("Sucess", null));
				outputStream.flush();
				break;
			case DELETE:
				delete(cc.getParameter());
				outputStream.writeObject(new ServerResponse("success", null));
				outputStream.flush();
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
		try {
			Cal cal = getCal();

			// Iterate through the collection of task objects.
			ListIterator<Task> listIterator = cal.tasks.listIterator();

			List<Task> tasks = new ArrayList<Task>();
			while (listIterator.hasNext()) 
			{
				Task t = listIterator.next();
				if(t.attendants.contains(attendant))	tasks.add(t);
			}     
			return tasks;

		} catch (JAXBException ex) {
			System.out.println("something went wrong!!!");
			Logger.getLogger(SimpleTcpServer.class.getName()).log(Level.SEVERE, null, ex);
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