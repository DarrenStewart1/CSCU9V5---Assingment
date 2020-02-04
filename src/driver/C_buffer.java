package driver;
import java.util.*;


/**
 * @author 2636161
 */

/**
 * The C_buffer class handles all of the requests that are made from the receiver it will allow requests to be saved into the buffer vector so that the requests can be processed by the Mutex
 * allowing a token to be sent to the selected node
 */
public class C_buffer {

	// Creates a new vector which stores objects
	private Vector<Object> data;

	public C_buffer() {
		
		// Creates an instance of the vector called data
		data = new Vector<Object>();
	}

	/**
	 * Class that will check the size of the buffer and return the value
	 * 
	 * @return The amount of elements that are in the buffer
	 */
	public int size() {

		// Returns the current amount of elements that are in the buffer
		return data.size();
	}

	/**
	 * Stores the port and ip of the node that made a request in the buffer 
	 * 
	 * @param r the request array which holds the ip and port number of the node that made the request           
	 */
	public synchronized void saveRequest(String[] r) {

		//adds the first element of the request array to the buffer
		data.add(r[0]);
		
		//adds the second element of the request array to the buffer
		data.add(r[1]);
	}

	/**
	 * Shows the current nodes that have a request in the buffer
	 */
	public void show() {

		// For loop that will display the content that is in the buffer
		for (int i = 0; i < data.size(); i++)
			System.out.print(" " + data.get(i) + " ");
		System.out.println(" ");
	}

	/**
	 * Method that will add an object to the data vector
	 * 
	 * @param o Object that is passed when the method is called
	 */
	public void add(Object o) {

		// adds an object to the request buffer
		data.add(o);
	}

	/** 
	 * If the data vector has an element in it this method will get the first element in the buffer and return it
	 * once the get method is called the element that is returned will be removed from the buffer.
	 * 
	 * @return returns the first element that is in the buffer
	 */
	synchronized public Object get() {

		Object o = null;

		// If statement that checks the size of the buffer and if it is greater than 0 an object will be created that contains the first element
		// of the buffer and will then remove that element from the buffer
		if (data.size() > 0) {
			o = data.get(0);
			data.remove(0);
		}
		return o;
	}

}
