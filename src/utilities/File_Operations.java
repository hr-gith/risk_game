package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * To read from file or write to file
 */

public class File_Operations {
	/**
	 * reads from a file and return its lines
	 * @param path which is the address of file
	 * @return  lines in the file
	 */
    public static ArrayList<String> Read_File(String path){
        ArrayList<String> file_content=new ArrayList<String>();
        
        try {
            BufferedReader reader=new BufferedReader(new FileReader(path));
            String readLine="";
            while ((readLine = reader.readLine()) != null) {
            	file_content.add(readLine);
            }
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println("File read successfully.");
        return file_content;
    
    }
    
    /**
     * write list of string in a file
     * if a file exists, it overwrite it
     * @param text which presents lines 
     * @param path which is the address of file
     */
    public static void Write_File(ArrayList<String> text, String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Config.output_file,false));
            for(String line: text)
            	writer.write(line+"\n");
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }    
}

