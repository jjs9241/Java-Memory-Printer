package fileIoByReflect.ioLogic;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fileIoByReflect.entity.Club;
import fileIoByReflect.entity.Member;
import fileIoByReflect.entity.Membership;
import fileIoByReflect.entity.Address;
import fileIoByReflect.util.DocumentUtil;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.io.FileNotFoundException;

public class IoMapStore implements IoStore {
    //
    private Map<String, Integer> membershipKIMap;
    private Map<String, Integer> addressKIMap;
    
    private Map<String, Member> memberMap;
    private Map<String, Club> clubMap;

    public IoMapStore() {
        //
        this.membershipKIMap = new LinkedHashMap<>();
        this.addressKIMap = new LinkedHashMap<>();
        
        this.clubMap = new LinkedHashMap<>();
        this.memberMap = new LinkedHashMap<>();
    }
    
    private Map<String, Object> collectObjects(){
    	
    	Map<String, Object> collectedObjects;
    	collectedObjects = new LinkedHashMap<String, Object>();
    	Club club = Club.getSample();
    	Member member = Member.getSample();
    	Member member1;
    	Membership membership1;
    	Membership membership = new Membership(club.getId(), member.getId());
    	club.addMembership(membership);
    	member.addMembership(membership);
    	try {
    		member1 = new Member("sample1@sample.com", "sample1 name", "010-0000-0001");
    		membership1 = new Membership(club.getId(), member1.getId());
    		club.addMembership(membership1);
    		member1.addMembership(membership1);
    		collectedObjects.put(member1.getId(), member1);
    	} catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	collectedObjects.put(club.getId(), club);
    	collectedObjects.put(member.getId(), member);
    	
    	return collectedObjects;
    }

    @Override
    public void saveToText() {
        //

    	Map<String, Object> collectedObjects = collectObjects();
        ObjectClassifier objectClassifier = ObjectClassifier.getInstance();
        objectClassifier.objectMapping(collectedObjects);
        
        Map<Class, LinkedHashSet<Object>> classSetMap = objectClassifier.getClassSetMap();

        Set classSet = classSetMap.keySet();
        Iterator iter = classSet.iterator();
        DocumentUtil documentUtil = new DocumentUtil();
        for(;iter.hasNext();) {
            Class classTemp = (Class)iter.next();
            LinkedHashSet<Object> objectSet = classSetMap.get(classTemp);
            objectSet.stream().forEach(object->{documentUtil.writeToText(classTemp.getCanonicalName(),objectClassifier.objectToString(object));});
        }

    }
    
    private Map mapAllFields(Class clazz) {
        Map<String, Field> fieldMap = new LinkedHashMap<String, Field>();
        Class tmpClass = clazz;
        while (tmpClass != null) {
            List<Field> tempFieldList = Arrays.asList(tmpClass .getDeclaredFields());
            for(Field field : tempFieldList) {
            	fieldMap.put(field.getName(), field);
            }
            tmpClass = tmpClass .getSuperclass();
        }
        return fieldMap;
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
}
