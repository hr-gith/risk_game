package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import models.Game_Model;

/**
 * To read from file or write to file
 */

public class File_Operations {
	/**
	 * reads from a file and return its lines
	 * 
	 * @param path
	 *            which is the address of file
	 * @return lines in the file
	 */
	public static ArrayList<String> Read_File(String path) {
		ArrayList<String> file_content = new ArrayList<String>();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String readLine = "";
			while ((readLine = reader.readLine()) != null) {
				file_content.add(readLine);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("File read successfully.");
		return file_content;

	}

	/**
	 * write list of string in a file if a file exists, it overwrite it
	 * 
	 * @param text
	 *            which presents lines
	 * @param path
	 *            which is the address of file
	 */
	public static void Write_File(ArrayList<String> text, String path) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(Config.output_file, false));
			for (String line : text)
				writer.write(line + "\n");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean Serialize(Object object, String filename) {
        try
        {    
            FileOutputStream file = new FileOutputStream(".\\src\\" +filename); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
              
            out.writeObject(object);               
            out.close(); 
            file.close();               
            System.out.println("Object has been saved.");   
            return true;
        }           
        catch(IOException ex) 
        { 
            System.out.println("IOException: "+ ex.getMessage()); 
            return false;
        } 
	}
	
	public static Game_Model Deserialize(String filename) {
		Game_Model obj = null;
        try
        {    
            FileInputStream file = new FileInputStream(".\\src\\" +filename); 
            ObjectInputStream in = new ObjectInputStream(file);               
            obj = (Game_Model)in.readObject(); 
              
            in.close(); 
            file.close(); 
              
            System.out.println("Game has been loaded."); 
        }           
        catch(IOException ex) 
        { 
            System.out.println("IOException: "+ ex.getMessage()); 
        }           
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException: "+ ex.getMessage()); 
        } 
        return obj;
	}
}
