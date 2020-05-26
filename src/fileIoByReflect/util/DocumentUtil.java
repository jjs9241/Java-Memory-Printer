package fileIoByReflect.util;

import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.util.LinkedList;

public class DocumentUtil {
    //
    public static String objectSeporator = "#";
    public static String typeKeySeporator = " : ";
    public static String fieldSeporator = ";";
    public static String keyValueSeporator = " = ";

    FileWriter output;

    public void writeToText(String filename, String content) {
        //
        try {
            this.output = new FileWriter("C:\\Users\\jjs92\\OneDrive\\바탕 화면\\취업\\Projects\\Nextree Internship Project\\src\\fileIoByReflect\\textfiles\\" + filename + ".txt", true);
            String[] lines = content.split(";");
            int j = lines.length-1;
            for(int i = 0; i<lines.length; i++) {
                StringBuilder builder = new StringBuilder();
                if(lines[i].trim().equals("[")){
                    builder.append(lines[i]).append("\n");
                } else if (i<j) {
                    builder.append(lines[i]).append(";").append("\n");
                } else {
                    builder.append(lines[i]).append("\n");
                }
                output.write(builder.toString());
            }
            output.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public LinkedList<String> readObjectFromText(RandomAccessFile rInput) {
        //
        LinkedList<String> object = new LinkedList<String>();

        try {
            String line = "";
            while(!line.trim().equals("#")) {
                line = rInput.readLine();
                if(line == null) {
                    object.add("#EOF#");
                    break;
                }
                object.add(line);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            object.add("#EOF#");
        }

        return object;
    }
    
    public String getObjectId(Object object) {
    	
    	return "object_id";
    }
}