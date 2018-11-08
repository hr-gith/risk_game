package models;

/**
 * this is a model class, which handles the string messages
 *
 */
public class Message_Handler {
	public boolean ok;
	public String message;

	/**
	 * constructor
	 * 
	 * @param ok
	 * @param message
	 */
	public Message_Handler(boolean ok, String message) {
		this.ok = ok;
		this.message = message;
	}

	/**
	 * constructor
	 * 
	 * @param ok
	 */
	public Message_Handler(boolean ok) {
		this.ok = ok;
		this.message = "";
	}

	/**
	 * sets message to the passed string
	 * 
	 * @param ok
	 * @param message
	 */
	public void Set(boolean ok, String message) {
		this.ok = ok;
		this.message = message;
	}
}
