package View;



import static org.junit.Assert.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import org.junit.Test;

/**
* this is a ijunit test class for Map_Generator_Controller
*
*/
public class World_Domination_ViewTest {
    
        JFrame jFrame =  new JFrame();
        JPanel jpanel = new JPanel();
        JLabel jLabel1 = new JLabel();
    
    @Test
    public void testLines() {
        
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        
        jFrame.setSize(screenSize.width/2, screenSize.height/2);
//        jFrame.setSize(500, 500);
        jFrame.setBackground(Color.blue);
        jFrame.setVisible(true);
        jFrame.setLayout(null);

        jpanel.setBounds(screenSize.width/50,screenSize.width/50, (screenSize.width/2)-(2*(screenSize.width/50)),  (screenSize.width/2)-((screenSize.width/50)*2));
//        jpanel.setBounds(45, 40, 400, 400);
        
        jpanel.setBackground(Color.yellow);

        jFrame.add(jpanel);
        
        String tmpString0 = "30% by Leila";
        
        String tmpString1 = "continent 1!";
        
        jpanel.add(jLabel1);
        jLabel1.setText("<html>" + tmpString0 +"<br>" + tmpString1 + "</html>");
//    
        
    }

}

