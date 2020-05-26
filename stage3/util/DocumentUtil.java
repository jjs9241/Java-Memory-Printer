package javastory.club.stage3.util;

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
            this.output = new FileWriter(filename + ".txt", true);
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

    /*public String objectToDocument(Object object) {
        //
        if(object == null) { return null; }

        StringBuilder builder = new StringBuilder();

        Field fields[] = object.getClass().getDeclaredFields();

        try {
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                builder.append(field.getType()).append(typeKeySeporator);
                builder.append(field.getName()).append(keyValueSeporator);
                Class fieldClass = field.getClass();
                if (fieldClass.isPrimitive()) {
                    builder.append(field.get(object));
                } else if(fieldClass.getSimpleName().equals("String")) {
                    //스티링처리가 휠씬 어려우니 추가할게 많이 남아있다. ㅠㅠ
                    builder.append("\"").append((String)field.get(object)).append("\"");
                } else {
                    builder.append(findSequence(fieldClass, field));
                }
                //builder.append(field.getDeclaringClass()).append(keyValueSeporator);
                //int mod = field.getModifiers();
                //System.out.println(Modifier.toString(mod));
                builder.append(fieldSeporator);
            }
        } catch(IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        builder.append(objectSeporator);

        return builder.toString();
    }

    private String listToDocument(List list) {
        //
        return null;
    }

    public ArrayList objectToStringList(Object object) {
        //

    }

    public ArrayList<String> containerToStringList(Object container){
        //first input must container, but next input don't need to be container
        ArrayList<String> arrayList = new ArrayList();

        if(Collector.class.isInstance(container) || container.getClass().isArray()) {
            arrayList.add("<"+container.getClass().getSimpleName()+">");
            if(Map.class.isInstance(container)) {
                if(((Map)container).isEmpty()){
                    Set keySet = ((Map)container).keySet();
                    Iterator iter = keySet.iterator();
                    for(;iter.hasNext();){
                        Object keyObject = iter.next();
                        arrayList.addAll(containerToStringList(keyObject));
                        arrayList.addAll(containerToStringList(((Map)container).get(keyObject)));
                    }
                }

                arrayList.add();
                List keyList = new ArrayList();
                keyList.addAll(((Map)container).keySet());

            }
            arrayList.addAll(containerToStringList(container));
            arrayList.add("<|"+container.getClass().getSimpleName()+"|>");
        } else {
            arrayList.add(objectToStringList(container));
        }
// 여러 종류의 class를 담을 수 있는 컨테이너는 생각하지 않는다.
        return arrayList;
    }

    //public ArrayList<String>*/
}