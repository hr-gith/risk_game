package models;

public class Message_Handler {
	public boolean ok;
	public String message;
	
	public Message_Handler(boolean ok, String message) {
		this.ok = ok;
		this.message = message;
	}
	public Message_Handler(boolean ok) {
		this.ok = ok;
		this.message = "";
	}
	
	public void Set(boolean ok, String message) {
		this.ok = ok;
		this.message = message;
	}
}
