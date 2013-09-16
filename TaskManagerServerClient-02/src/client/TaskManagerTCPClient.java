/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author Lauge Djuraas (ladj), Claes Martinsen (clae), Christian Ole Kirschberg (colk), Eskandar Pahlavan Afshari(epaa)
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import taskObjects.ClientCall;
import taskObjects.FunctionName;
import taskObjects.ServerResponse;
import taskObjects.Task;

public class TaskManagerTCPClient {
	public static void main(String args[]) throws ClassNotFoundException  {


		ClientCall ccGet = new ClientCall(FunctionName.GET, "student-02");
		ServerResponse srGet = startClient(ccGet);

		// Print the message.
		System.out.println("Message from Server: " + srGet.getMessage());
		for(Task t : (List<Task>) srGet.getObject()) 
			System.out.println(t.ToString());

		/*
		Task task = new Task("42","test task","12-12-2013", "active", "this is a description", "group awesome");
		ClientCall ccPut = new ClientCall(FunctionName.PUT, "student-02",task);
        ServerResponse srPut = startClient(ccPut);
		System.out.println(srPut.getMessage());


        ClientCall ccDelete = new ClientCall(FunctionName.DELETE, "42");
        ServerResponse srDelete = startClient(ccDelete);
        System.out.println("delete = " + srDelete.getMessage());

		Task task1 = new Task("qualify-for-exam","test task","12-12-2013", "expired", "this is a description", "group awesome");

        ClientCall ccPost = new ClientCall(FunctionName.POST, "", task1);
        ServerResponse srPost = startClient(ccPost);
        System.out.println(srPost.getMessage());
		 */

	}

	public static ServerResponse startClient(ClientCall cc) throws ClassNotFoundException{
		System.out.println("Starting client");
		try {
			InetAddress serverAddress = InetAddress.getByName("localhost"); // IP address of the server,

			int serverPort = 7896; // It is the same port where server will be listening.

			Socket socket = new Socket(serverAddress, serverPort); // Open a socket for communication.

			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(cc);
			oos.flush();

			// Now switch to listening mode for receiving message from server.
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			if(!ois.readObject().equals(cc)){ 
				System.out.println("Server cannot confirm command");
			}
			else System.out.println("Command confirmed");

			System.out.println("Client is waiting for server response");
			// Note that this is a blocking call,  
			ServerResponse sr =  (ServerResponse) ois.readObject();

			// Finnaly close the socket. 
			socket.close();

			return sr;

		} catch (IOException ex) {
			Logger.getLogger(TaskManagerTCPClient.class.getName()).log(Level.SEVERE, null, ex);

			System.out.println("error message: " + ex.getMessage());
			return null;
		}
	}
}