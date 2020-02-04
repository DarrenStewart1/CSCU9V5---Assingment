package driver;

import java.io.IOException;
import java.net.*;

/**
 * @author 2636161
 */


/**
 * This class is used to accept a request from the node for a token it will also be the start point
 * to run an instance of the connection class
 */
public class C_receiver extends Thread {

	private C_buffer buffer;
	private int port;
	private ServerSocket s_socket;
	private Socket socketFromNode;
	private C_Connection_r connect;

	/**
	 * Constructor for the C_receiver class it will use the buffer and port as its parameters
	 * 
	 * @param b the buffer that the requests are stored in
	 * 
	 * @param p the port number that the receiver uses
	 */
	public C_receiver(C_buffer b, int p) {
		buffer = b;
		port = p;
	}
	
	/* 
	 * When a receiver is started the code in this method is started and will run in its own thread this method is responsible 
	 * for creating the socket that the receiver listens on and accepting requests that are made to the coordinator 
	 */
	public void run() {

		// >>> create the socket the server will listen to

		// Creates a socket that the server will listen to use using the port number of the receiver
		try {
			s_socket = new ServerSocket(port);		
		} 
		
		// Displays an error message to the screen if the server could not connect to the port
		catch (IOException e) {
			System.out.println("C:Receiver connection could not be made problem with the server socket" + e);
			
			e.printStackTrace();
		}

		while (true) {

			try {
				// >>> get a new connection

				// Accepts the request from the server socket
				socketFromNode = s_socket.accept();
				// Display a message showing that the coordinator received request 
				System.out.println("C:Receiver Coordinator has received a request ...");

				// >>> create a separate thread to service the request, a C_Connection_r thread.

				// Creates a new instance of C_Connection_r
				connect = new C_Connection_r(socketFromNode, buffer);
				//Start point for the execution of code that is in the connections run method this will run it is own thread
				connect.start();
			} 
			
			// Displays an error message when the server socket encounters a problem when accepting the request from the socket
			catch (java.io.IOException e) {
				
				System.out.println("C:Receiver Crashed when creating a connection " + e);
				
				e.printStackTrace();
			}
		}
	}
}
