package driver;

import java.net.*;

/**
 * @author 2636161
 */

/**
 * This class is used to start the coordinator in the system it is used to start
 * the Receiver which will listen for connections it is also used to start the Mutex so that the requests can be processed
 */
public class Coordinator {

	/**
	 * This method contains the code that is used to launch the coordinator it will define the launch ports that are used by the 
	 * Mutex and Receiver class it is also responsible for creating instances and the execution of the receiver and Mutex 
	 * 
 
	 *  @param args contains the launch parameters that are used by the Coordinator class
	 *  
	 *  DEFINE THE LAUNCH PARAMETERS OF THIS CLASS AS 7000 7001
	 */
	public static void main(String args[]) {

		// Creates a new instance of the coordinator
		Coordinator c = new Coordinator();
		// Creates a new instance of the buffer
		C_buffer buffer = new C_buffer();

		try {
			// Retrieves the host name that the Coordinator uses
			InetAddress c_addr = InetAddress.getLocalHost();
			
			// Retrieves the ip that the Coordinator uses 
			String c_name = c_addr.getHostName();
			
			// Displays the ip address of the Coordinator
			System.out.println("Coordinator address is " + c_addr);
			
			// Displays the host name of the Coordinator
			System.out.println("Coordinator host name is " + c_name + "\n\n");
		} 
		// Display an error message if the ip or address of the Coordinator could not be retrieved
		catch (Exception e) {
			System.err.println(e);
			System.err.println("Error in corrdinator");
		}
		
		// Defines the Receiver port at launch time this will assigns the value that was assigned to the first element in the args array then converting the value to an integer then storing it in the receiver port variable
		int receiverPort = Integer.parseInt(args[0]);
		
		// Defines the Mutex port at launch time this will assigns the value that was assigned to the second element in the args array converting the value to an integer then storing it in the mutexPort variable
		int mutexPort = Integer.parseInt(args[1]);

		//>>>> Create and run a C_receiver and a C_mutex object sharing a C_buffer object

		// Creates an Instance of the Mutex which passes the buffer and the port that has been assigned to the Mutex
		C_mutex mutex = new C_mutex(buffer, mutexPort);
		
		// Creates an instance of the Receiver which passes the buffer and the port that was assigned to the receiver
		C_receiver receiver = new C_receiver(buffer, receiverPort);
	
		// Start point for the execution of code that is in the Receivers run method this will run it is own thread
		receiver.start();
				
		// Start point for the execution of code that is in the Mutex run method this will run it is own thread
		mutex.start();

		// Delays the coordinator for half a second
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println("Error when sleeping the thread");
			e.printStackTrace();
		}

	}

}
