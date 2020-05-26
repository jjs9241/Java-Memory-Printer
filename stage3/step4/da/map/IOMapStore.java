package javastory.club.stage3.step4.da.map;

import javastory.club.stage3.step1.entity.board.Posting;
import javastory.club.stage3.step1.entity.board.SocialBoard;
import javastory.club.stage3.step1.entity.club.*;
import javastory.club.stage3.step4.da.map.io.MemoryMap;
import javastory.club.stage3.step4.da.map.io.ObjectClassifier;
import javastory.club.stage3.step4.store.IOStore;
import javastory.club.stage3.util.DocumentUtil;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class IOMapStore implements IOStore {
    //
    private Map<String, Integer> membershipKIMap;
    private Map<String, Integer> addressKIMap;

    public IOMapStore() {
        //
        this.membershipKIMap = new LinkedHashMap<>();
        this.addressKIMap = new LinkedHashMap<>();
    }

    @Override
    public void saveToText() {
        //

        ObjectClassifier objectClassifier = ObjectClassifier.getInstance();
        objectClassifier.objectMapping(boardMap);
        objectClassifier.objectMapping(postingMap);
        objectClassifier.objectMapping(clubMap);
        objectClassifier.objectMapping(memberMap);

        Set classSet = classSetMap.keySet();
        Iterator iter = classSet.iterator();
        DocumentUtil documentUtil = new DocumentUtil();
        for(;iter.hasNext();) {
            Class classTemp = (Class)iter.next();
            LinkedHashSet<Object> objectSet = classSetMap.get(classTemp);
            objectSet.stream().forEach(object->{documentUtil.writeToText(classTemp.getCanonicalName(),objectClassifier.objectToString(object));});
        }

    }

    @Override
    public void saveToSimpleText() {
        //
        ObjectClassifier objectClassifier = ObjectClassifier.getInstance();
        objectClassifier.objectMapping(boardMap);
        objectClassifier.objectMapping(postingMap);
        objectClassifier.objectMapping(clubMap);
        objectClassifier.objectMapping(memberMap);

        DocumentUtil documentUtil = new DocumentUtil();

        Iterator iter = mainClassSet.iterator();
        for(;iter.hasNext();) {
            Class classTemp = (Class) iter.next();
            LinkedHashSet<Object> objectSet = classSetMap.get(classTemp);
            if (objectSet != null) {
                objectSet.stream().forEach(object -> {
                    documentUtil.writeToText(classTemp.getCanonicalName(), objectClassifier.objectToSimpleString(object));
                });
            }
        }
    }

    @Override
    public void loadTextfile() {
        //
        Class[] mainClasses = { TravelClub.class, CommunityMember.class, ClubMembership.class, Posting.class, SocialBoard.class, Address.class };

        ArrayList<TravelClub> clubList = new ArrayList<>();
        ArrayList<CommunityMember> memberList = new ArrayList<>();
        ArrayList<ClubMembership> membershipList = new ArrayList<>();
        ArrayList<Posting> postingList = new ArrayList<>();
        ArrayList<SocialBoard> boardList = new ArrayList<>();
        ArrayList<Address> addressList = new ArrayList<>();
        ArrayList[] listArray = { clubList, memberList, membershipList, postingList, boardList, addressList };
        LinkedList<String> referenceList = new LinkedList<>();

        try {
            DocumentUtil documentUtil = new DocumentUtil();

            RandomAccessFile rInput;

            for (int i = 0; i < mainClasses.length; i++) {
                //
                try {
                    rInput = new RandomAccessFile(mainClasses[i].getCanonicalName() + ".txt", "r");

                    while (Boolean.TRUE) {
                        Constructor constructor = mainClasses[i].getDeclaredConstructor();
                        constructor.setAccessible(true);
                        Object object = constructor.newInstance();
                        LinkedList<String> objectString = documentUtil.readObjectFromText(rInput);
                        if (objectString.get(0).equals("#EOF#")) {
                            referenceList.add("#EOF#");
                            break;
                        } else if (!objectString.get(0).equals("#")) {
                            fillingFieldValue(object, objectString);
                            listArray[i].add(object);
                        }
                        referenceList.addAll(objectString);
                    }

                    rInput.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    referenceList.add("#EOF#");
                }
            }

            fillingFieldReference(listArray, referenceList, mainClasses);
            /*Iterator iter = referenceList.iterator();
            for (int i = 0; i < mainClasses.length; i++) {
                //
                String line = "";
                int index = 0;
                while(!line.equals("#EOF#")) {
                    line = (String)iter.next();
                    if(line.trim().equals("#")) {
                        index++;
                        continue;
                    }
                    if(line.trim().equals("#EOF#")){
                        break;
                    }
                    String[] splitedField = line.split(":");
                    String className = splitedField[0].trim();
                    splitedField = splitedField[1].split("=");
                    String keyName = splitedField[0].trim();
                    splitedField = splitedField[1].split(";");
                    String valOrIndex = splitedField[0];
                    Class fieldClass = Class.forName(className.trim());

                    if(valOrIndex.trim().equals("[")) {
                        //mainClasses[i].getField(keyName).set(listArray[i].get(index), new ArrayList());
                        Field f = mainClasses[i].getDeclaredField(keyName);
                        f.setAccessible(true);
                        ArrayList arrList = (ArrayList)(f.get(listArray[i].get(index)));
                        String arrayField = "";
                        while(Boolean.TRUE){
                            arrayField = (String)iter.next();
                            if(arrayField.trim().equals("];")){
                                break;
                            }
                            String[] splitArrayField = arrayField.split(":");
                            String afClassName = splitArrayField[0].trim();
                            splitArrayField = splitArrayField[1].split("=");
                            String cfKeyName = splitArrayField[0].trim();
                            splitArrayField = splitArrayField[1].split(";");
                            String afValOrIndex = splitArrayField[0];

                            Class arrayFieldClass = Class.forName(afClassName);

                            Object fieldObject = arrayFieldClass.newInstance();
                            if (arrayFieldClass.isPrimitive() || (fieldObject instanceof Number && Modifier.isFinal(arrayFieldClass.getModifiers()))) {
                                arrList.add(NumberFormat.getInstance().parse(afValOrIndex.trim()));
                            } else if (arrayFieldClass.getSimpleName().equals("String")) {
                                arrList.add(afValOrIndex);
                            } else if (arrayFieldClass == Boolean.class) {
                                arrList.add(Boolean.valueOf(afValOrIndex.trim()));
                            } else {
                                int fieldClassIndex = -1;
                                for(int j = 0; j<mainClasses.length; j++) {
                                    if(mainClasses[j] == arrayFieldClass) {
                                        fieldClassIndex = j;
                                    }
                                }
                                int m = Integer.parseInt(afValOrIndex.trim())-1;
                                arrList.add(listArray[fieldClassIndex].get(Integer.parseInt(afValOrIndex.trim())-1));
                            }
                        }
                    } else {
                        int fieldClassIndex = -1;
                        for(int j = 0; j<mainClasses.length; j++) {
                            if(mainClasses[j] == fieldClass) {
                                fieldClassIndex = j;
                            }
                        }
                        mainClasses[i].getField(keyName).set(listArray[i].get(index), listArray[fieldClassIndex].get(Integer.parseInt(valOrIndex)));
                    }

                    //fillingFieldReference(listArray[i].get(index), );
                }
            }*/

            for(TravelClub club : clubList) {
                clubMap.put(club.getId(), club);
            }

            for(SocialBoard board : boardList) {
                boardMap.put(board.getId(), board);
            }

            for(CommunityMember member : memberList) {
                memberMap.put(member.getId(), member);
            }

            for(Posting posting : postingList) {
                postingMap.put(posting.getId(), posting);
            }

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            System.out.println(e.getMessage());
        }
    }

    private void fillingFieldValue(Object object, LinkedList<String> objectFields) {
        //
        Class objectClass = object.getClass();

        try {
            int deleteFields = 0;
            int readFields = 0;
            while(objectFields.size() != readFields - deleteFields) {
                String stringField = objectFields.get(readFields - deleteFields);
                if(stringField.trim().equals("#")){
                    return;
                }
                String[] splitedField = stringField.split(":");
                String className = splitedField[0];
                splitedField = splitedField[1].split("=");
                String keyName = splitedField[0];
                splitedField = splitedField[1].split(";");
                String valOrIndex = splitedField[0];

                if(valOrIndex.trim().equals("[")) {
                    while(!objectFields.get(readFields - deleteFields).trim().equals("];")){
                        readFields++;
                    }
                } else {
                    Class fieldClass = Class.forName(className.trim());
                    Object fieldObject;

                    if(fieldClass.isEnum()) {
                        Field f = objectClass.getDeclaredField(keyName.trim());
                        f.setAccessible(true);
                        //fieldClass.values();
                        //Enum.valueOf(fieldClass, valOrIndex);
                        f.set(object, Enum.valueOf(fieldClass, valOrIndex.trim()));
                        objectFields.remove(readFields - deleteFields);
                        deleteFields++;
                    } else {
                        try {
                            Constructor constructor = fieldClass.getDeclaredConstructor();
                            constructor.setAccessible(true);
                            fieldObject = constructor.newInstance();
                            //fieldObject = fieldClass.newInstance();
                        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                            try {
                                Constructor constructor = fieldClass.getDeclaredConstructor(fieldClass.getDeclaredField("value").getType());
                                constructor.setAccessible(true);
                                fieldObject = constructor.newInstance(1);
                            } catch (InstantiationException | InvocationTargetException | NoSuchMethodException e1) {
                                fieldObject = fieldClass.newInstance();
                                System.out.println(e.getMessage());
                            }
                        }
                        Field f;
                        f = objectClass.getDeclaredField(keyName.trim());
                        if (Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())) {
                            objectFields.remove(readFields - deleteFields);
                            deleteFields++;
                        } else if (fieldClass.isPrimitive() || (fieldObject instanceof Number && Modifier.isFinal(f.getModifiers()))) {
                            f.setAccessible(true);
                            f.set(object, NumberFormat.getInstance().parse(valOrIndex.trim()));
                            objectFields.remove(readFields - deleteFields);
                            deleteFields++;
                        } else if (fieldClass.getSimpleName().equals("String")) {
                            f.setAccessible(true);
                            valOrIndex = getOriginString(valOrIndex);
                            f.set(object, valOrIndex);
                            objectFields.remove(readFields - deleteFields);
                            deleteFields++;
                        } else if (fieldClass == Boolean.class) {
                            f.setAccessible(true);
                            f.set(object, Boolean.valueOf(valOrIndex.trim()));
                            objectFields.remove(readFields - deleteFields);
                            deleteFields++;
                        }
                    }
                }
                readFields++;
            }
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException | ParseException | InstantiationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void fillingFieldReference(ArrayList[] listArray, LinkedList<String> referenceList, Class[] mainClasses) {
        //
        try {
            Iterator iter = referenceList.iterator();
            for (int i = 0; i < mainClasses.length; i++) {
                //
                String line = "";
                int index = 0;
                while (!line.equals("#EOF#")) {
                    line = (String) iter.next();
                    if (line.trim().equals("#")) {
                        index++;
                        continue;
                    }
                    if (line.trim().equals("#EOF#")) {
                        break;
                    }
                    String[] splitedField = line.split(":");
                    String className = splitedField[0].trim();
                    splitedField = splitedField[1].split("=");
                    String keyName = splitedField[0].trim();
                    splitedField = splitedField[1].split(";");
                    String valOrIndex = splitedField[0];
                    Class fieldClass = Class.forName(className.trim());

                    if (valOrIndex.trim().equals("[")) {
                        //mainClasses[i].getField(keyName).set(listArray[i].get(index), new ArrayList());
                        Field f = mainClasses[i].getDeclaredField(keyName);
                        f.setAccessible(true);
                        ArrayList arrList = (ArrayList) (f.get(listArray[i].get(index)));
                        String arrayField = "";
                        while (Boolean.TRUE) {
                            arrayField = (String) iter.next();
                            if (arrayField.trim().equals("];")) {
                                break;
                            }
                            String[] splitArrayField = arrayField.split(":");
                            String afClassName = splitArrayField[0].trim();
                            splitArrayField = splitArrayField[1].split("=");
                            String cfKeyName = splitArrayField[0].trim();
                            splitArrayField = splitArrayField[1].split(";");
                            String afValOrIndex = splitArrayField[0];

                            Class arrayFieldClass = Class.forName(afClassName);

                            Object fieldObject = arrayFieldClass.newInstance();
                            if (arrayFieldClass.isPrimitive() || (fieldObject instanceof Number && Modifier.isFinal(arrayFieldClass.getModifiers()))) {
                                arrList.add(NumberFormat.getInstance().parse(afValOrIndex.trim()));
                            } else if (arrayFieldClass.getSimpleName().equals("String")) {
                                arrList.add(afValOrIndex);
                            } else if (arrayFieldClass == Boolean.class) {
                                arrList.add(Boolean.valueOf(afValOrIndex.trim()));
                            } else {
                                int fieldClassIndex = -1;
                                for (int j = 0; j < mainClasses.length; j++) {
                                    if (mainClasses[j] == arrayFieldClass) {
                                        fieldClassIndex = j;
                                    }
                                }
                                int m = Integer.parseInt(afValOrIndex.trim()) - 1;
                                arrList.add(listArray[fieldClassIndex].get(Integer.parseInt(afValOrIndex.trim()) - 1));
                            }
                        }
                    } else {
                        int fieldClassIndex = -1;
                        for (int j = 0; j < mainClasses.length; j++) {
                            if (mainClasses[j] == fieldClass) {
                                fieldClassIndex = j;
                            }
                        }
                        mainClasses[i].getField(keyName).set(listArray[i].get(index), listArray[fieldClassIndex].get(Integer.parseInt(valOrIndex)));
                    }

                    //fillingFieldReference(listArray[i].get(index), );
                }
            }
        } catch (ClassNotFoundException | NoSuchFieldException | ParseException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    private String getOriginString(String quotesedString) {
        //
        int startingIndex = -1;
        int finishingIndex = -1;

        char[] charArray = quotesedString.toCharArray();
        for(int i = 0; i < charArray.length; i++) {
            if (charArray[i] == '\"') {
                startingIndex = i+1;
                break;
            }
        }
        for(int i = charArray.length - 1; -1 < i; i--) {
            if (charArray[i] == '\"') {
                finishingIndex = i-1;
                break;
            }
        }
        if(finishingIndex <= startingIndex) {
            System.out.println(quotesedString + " is wrong string.");
            return null;
        }
        return quotesedString.substring(startingIndex, finishingIndex + 1);
    }

    /*private void fillingFieldReference(Object object, String referenceField) {
        //
        Class objectClass = object.getClass();
        if (objectClass.isArray()){
            List<Field> fields = ObjectClassifier.getAllFields(objectClass);
            if(fields.size() == 0) {
                int i = 0;
                for(String stringField : objectFields) {
                    String[] splitedField = stringField.split(":=;");
                    String className = splitedField[0];
                    String keyName = splitedField[1];
                    String valOrIndex = splitedField[2];
                    //((Object[])object)[i] = ;
                }
            }
        }
    }*/
}
