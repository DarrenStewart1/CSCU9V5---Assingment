package driver;

import java.net.*;
import java.io.*;

/**
 * @author 2636161
 */

/**
 * Class that will handle all of the requests that are made from the nodes it
 * will take in the port and ip of the node and save them in the buffer
 */
public class C_Connection_r extends Thread {

	// class variables
	C_buffer buffer;
	Socket s;
	InputStream in;
	BufferedReader bin;
	
	/**
	 * Constructor that is used for the connection class it contains the socket that it is listening on and the buffer
	 * 
	 * @param s holds the host and port number of the node that has made a request
	 * 
	 * @param b the buffer that is used to store request that are made
	 */
	public C_Connection_r(Socket s, C_buffer b) {
		
		this.s = s;
		this.buffer = b;

	}

	/* Method for the code that will be executed when an instance of the the connection class is started
	 * The method reads in the ip address and port number of the node that made a request and stores them in the buffer.
	 */
	public void run() {

		// Variables used to assign the elements on the request array
		final int NODE = 0;
		final int PORT = 1;

		// Array that holds the value of the node and port
		String[] request = new String[2];

		// Message that is displayed showing that a request has been received from a node
		System.out.println("C:connection IN  dealing with request from socket " + s);
		try {

			// >>> read the request, i.e. node ip and port from the socket s

			// Creates a new instance of the input stream that uses the socket that was passed in by the connection class
			in = s.getInputStream();
			
			// Creates a new instance of the bufferedreader
			bin = new BufferedReader(new InputStreamReader(in));
			
			// Retrieves the ip address of the node that made the request and converts it to a string it will then create a substring ignoring the first character in the string which is '/' and stores it in the first element of the request array
			request[NODE] = (s.getInetAddress().toString().substring(1));
						
			// Retrieves the port number of the node that made the request and stores it in the second element of the request array
			request[PORT] = bin.readLine();
			
			// >>> save it in a request object and save the object in the buffer (see C_buffer's methods).

			// Saves the request array made up of the nodes ip and port number in the buffer		
			buffer.saveRequest(request);
			
			// Closes the connection that the input stream is listening on
			s.close();

			// Message that displays that the request has been recorded
			System.out.println("C:connection OUT received and recorded request from " + request[NODE] + ":" + request[PORT] + "  (socket closed)");

		} 
		// Exception that handles errors when creating a connection to the socket
		catch (java.io.IOException IOE) {

			System.out.println("C:Connection Problem with retriveing the node or port number of the request please make sure that the node is valid");
			System.out.println(IOE);
			IOE.printStackTrace();
			System.exit(1);
		}

	} // end of run() method

} // end of class
