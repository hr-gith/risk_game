package views;

import java.util.Observer;

import javax.swing.JPanel;

public abstract class View extends JPanel implements Observer {
	public abstract void Update_Display(String text);
	

}
