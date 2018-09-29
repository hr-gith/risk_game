package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class File_Operations {
    public static ArrayList<String> Read_File(String path){
        ArrayList<String> file_content=new ArrayList<String>();
        try {
            BufferedReader reader=new BufferedReader(new FileReader(path));
            String readLine="";
            while ((readLine = reader.readLine()) != null) {
            	file_content.add(readLine);
                //characters=readLine.split(" ");
                //characters.replaceAll("\\s","");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //System.out.println("File read successfully.");
        return file_content;
    }
    
    public static void Write_File(ArrayList<String> text, String path) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(Config.output_file,true));
            for(String line: text)
            	writer.write(line);
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }    
}

