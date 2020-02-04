package driver;

import java.net.*;

/**
 * @author 2636161
 */

/**
 * The C_mutex class handles what node will receive the token and send the token to the node that was chosen this method also handles getting the token back from the node
 */
public class C_mutex extends Thread {

	
	C_buffer buffer;
	Socket s;
	int port;
	ServerSocket ss_back;

	// ip address and port number of the node requesting the token.
	// They will be fetched from the buffer
	String n_host;
	int n_port;

	/**
	 * Class that selects the request that the token will be sent to this is done by
	 * getting the host and port number of the first element of the buffer
	 * 
	 * @param b the buffer that is used to store the requests
	 * 
	 * @param p the port number that the mutex uses
	 */
	public C_mutex(C_buffer b, int p) {

		buffer = b;
		port = p;
	}
	
	/**
	 * Method that is responsible for getting the requests that are made for the buffer and sending the token
	 * to the requests once the node completes it critical section this method will also handle retrieved the token that was 
	 * returned by the node
	 */
	public void go() {

		try {
			// >>> Listening from the server socket on port 7001
			// from where the TOKEN will be later on returned.
			// This place the server creation outside he while loop.

			// Creates a server socket that listens for requests on port 7001
			ss_back = new ServerSocket(port);

			while (true) {

				// >>> Print some info on the current buffer content for debugging purposes.
				// >>> please look at the available methods in C_buffer

				// delay so that the contents of the buffer is checked every 15 seconds
				Thread.sleep(15000);

				// Message to make the display of the buffers content clearer
				System.out.println("");	
				System.out.println("---------------------------------DISPLAYING CONTENTS OF BUFFER----------------------------------");
				
				// message that shows the amount of requests that are currently stored in the buffer
				System.out.println("C:mutex Buffer size is " + buffer.size());

				// message that displays the current nodes that are in the buffer
				System.out.println("C:Mutex Current contents of the buffer");
				
				//calls the buffers show method which will display the content of the buffer
				buffer.show();
				
				System.out.println("------------------------------------------------------------------------------------------------");
				System.out.println("");	

				// checks the size of the buffer and if the size of the buffer is greater than 0  it will execute the code in the if statement
				if (buffer.size() > 0) {

					// >>> Getting the first (FIFO) node that is waiting for a TOKEN form the buffer
					// Type conversions may be needed.

					// Retrieves the host of the first node in the buffer queue and stores it in the n_host variable
					n_host = (String) buffer.get();

					// Retrieves the port number of the first node in the buffer then converts it to an int value and stores it in the n_port variable
					n_port = Integer.parseInt((String) buffer.get());

					// >>> **** Granting the token
								
					try {
						//Message that is displayed when a connection is being made to a node
						System.out.println("C:mutex Connection being made to the node");

						// creates a connection to the node that was the first in the buffer queue
						s = new Socket(n_host, n_port);
						
						//closes the socket that the node made to the mutex
						s.close();

						// Message that confirms which node that the token was sent to
						System.out.println("C:mutex token Sending TOKEN to " + n_host + " " + n_port);
					}

					//Handles errors caused when the mutex is unable to grant the token
					catch (java.io.IOException e) {
						System.out.println(e);
						
						System.out.println("CRASH Mutex connecting to the node for granting the TOKEN" + e);
					}

					// >>> **** Getting the token back

					try {				
						// Accepts the connection from the server that sends the token back
						s = ss_back.accept();

						// Closes the connection of the socket that returned the token
						s.close();
										
						//Message that shows which node returned the token to the coordinator
						System.out.println("C:mutex returning TOKEN from " + n_host + " " + n_port);

						//error message that is displayed if the token cannot be returned
					} catch (java.io.IOException e) {
						System.out.println(e);
						
						System.out.println("CRASH Mutex waiting for the TOKEN back" + e);
					}
				}
				else

					// Message that will be displayed if the que is empty
					System.out.println("No requests in que");			
			}

			// Display an error message if request cannot be retrieved from the buffer
		} catch (Exception e) {

			System.out.print(e);

			e.printStackTrace();
			
			System.out.println("Error when retriving request from buffer");		
		}

	}
   //When a instance of mutex is started the code that is in this method will be run in its own thread
	public void run() {

		go();
	}

}
