/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author rao
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import taskObjects.ClientCall;
import taskObjects.ServerResponse;
import taskObjects.Task;

public class SimpleTcpClient {
	

    public static void main(String args[]) throws ClassNotFoundException  {
        try {
            // IP address of the server,
            InetAddress serverAddress = InetAddress.getByName("localhost");
            
            // It is the same port where server will be listening.
            int serverPort = 7896;
            
            
            
            
            
            // Open a socket for communication.
            Socket socket = new Socket(serverAddress, serverPort);
                
            //Call GET function
            String messageFunction = "GET";
            String attendant = "student-02";
            
            ClientCall cc = new ClientCall(messageFunction, attendant);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(cc);
            oos.flush();
            
                        
            // Now switch to listening mode for receiving message from server.
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
           // Note that this is a blocking call,  
            ServerResponse sr =  (ServerResponse) ois.readObject();
            
            
            // Print the message.
            System.out.println("Message from Server: " + sr.getMessage());
            
            for(Task t : (List<Task>) sr.getObject()) 
            	System.out.println(t.ToString());
            
            
            
            
            // Finnaly close the socket. 
            socket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(SimpleTcpClient.class.getName()).log(Level.SEVERE, null, ex);
            
            System.out.println("error message: " + ex.getMessage());
        } 
        
        
    }
}