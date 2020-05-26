package fileIoByReflect.ioLogic;

//import javastory.entity.AutoIdEntity;
//import javastory.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import fileIoByReflect.entity.Entity;
import fileIoByReflect.util.DocumentUtil;

//import static DocumentUtil.typeKeySeporator;

public class ObjectClassifier {

    private static ObjectClassifier singletonClassifier;

    private Map<Class, LinkedHashSet<Object>> classSetMap;
    private Map<Object, Integer> objectSequenceMap;

    public ObjectClassifier() {
        this.classSetMap = new LinkedHashMap<>();
        this.objectSequenceMap = new LinkedHashMap<>();
    }

    public static ObjectClassifier getInstance() {
        //
        if (singletonClassifier == null) {
            singletonClassifier = new ObjectClassifier();
        }

        return singletonClassifier;
    }
    
    public void objectMapping(Object object) {
    	
    	Class objectClass = object.getClass();
    	Set keySet = classSetMap.keySet();
    	if(!keySet.contains(objectClass)) {
    		classSetMap.put(objectClass, new LinkedHashSet<Object>());
    	}
    	if(!classSetMap.get(objectClass).contains(object)) {
    		classSetMap.get(objectClass).add(object);
    		objectSequenceMap.put(object, classSetMap.get(objectClass).size());
    	}
    	
    	List<Field> fields = getAllFields(object.getClass());
    	
    	if(object.getClass().isArray() && fields.size() == 0) {
        	Object[] objectArray = (Object[])object;
        	int arrayLen = objectArray.length;
        	for(int i = 0; i<arrayLen; i++){
                if(objectArray[i] != null) {
                	objectMapping(objectArray[i]);
                }
            }
    	}
    	
    	try {
            for (Field field: fields) {

                field.setAccessible(true);
                Object fieldObject = field.get(object);
                if (fieldObject != null) {
                    Class fieldClass = fieldObject.getClass();
                    System.out.println(fieldClass.getSimpleName());
                    if (fieldClass.isPrimitive() || (fieldObject instanceof Number && Modifier.isFinal(fieldClass.getModifiers())) || fieldClass == Boolean.class) {
                        System.out.println("This field is Primitve.");
                    } else if (fieldClass.getSimpleName().equals("String")) {
                        System.out.println("This field is String.");
                    } else if (fieldClass.isEnum()) {
                        System.out.println("This field is Enum.");
                    } else if (!(objectSequenceMap.keySet().contains(fieldObject))){
                    	objectMapping(fieldObject);
                    }
                }
            }
        } catch(IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    	
    }

    public String objectToString(Object object) {
        //
        String objectSeporator = "#";
        String typeKeySeporator = " : ";
        String fieldSeporator = ";";
        String keyValueSeporator = " = ";

        if(object == null) { return null; }

        StringBuilder builder = new StringBuilder();

        //Field fields[] = object.getClass().getDeclaredFields();
        List<Field> fields = getAllFields(object.getClass());

        if(object.getClass().isArray() && fields.size() == 0) {
        	Object[] objectArray = (Object[])object;
        	int arrayLen = objectArray.length;
        	builder.append(arrayLen).append(fieldSeporator);
        	for(int i = 0; i<arrayLen; i++){
                if(objectArray[i] != null) {
                	Class tempClass = objectArray[i].getClass();
                	builder.append(objectArray[i].getClass().getCanonicalName()).append(typeKeySeporator);
                    builder.append(i).append(keyValueSeporator);
                    if (tempClass.isPrimitive() || (objectArray[i] instanceof Number && Modifier.isFinal(tempClass.getModifiers())) || tempClass == Boolean.class || tempClass.isEnum()) {
                        builder.append(objectArray[i]);
                    } else if (tempClass.getSimpleName().equals("String")) {
                        //스티링처리가 휠씬 어려우니 추가할게 많이 남아있다. ㅠㅠ
                        builder.append("\"").append((String) objectArray[i]).append("\"");
                    } else {
                        builder.append(findSequence(objectArray[i]));
                    }
                    builder.append(fieldSeporator);
                }
            }
    	}
        
        try {
            for (Field field: fields) { //(int i = 0; i < fields.length; i++) {
                //Field field = fields[i];
                field.setAccessible(true);
                Object fieldObject = field.get(object);
                if (fieldObject != null) {
                    builder.append(fieldObject.getClass().getCanonicalName()).append(typeKeySeporator);
                    builder.append(field.getName()).append(keyValueSeporator);
                    Class fieldClass = fieldObject.getClass();
                    if (fieldClass.isPrimitive() || (fieldObject instanceof Number && Modifier.isFinal(fieldClass.getModifiers())) || fieldClass == Boolean.class || fieldClass.isEnum()) {
                        builder.append(fieldObject);
                    } else if (fieldClass.getSimpleName().equals("String")) {
                        //스티링처리가 휠씬 어려우니 추가할게 많이 남아있다. ㅠㅠ
                        builder.append("\"").append((String) fieldObject).append("\"");
                    } else if (fieldObject != null){
                        builder.append(findSequence(fieldObject));
                    }
                    if (fieldObject.getClass().isArray() && getAllFields(fieldObject.getClass()).size() == 0) {
                    	Object[] objectArray = (Object[])fieldObject;
                    	int arrayLen = objectArray.length;
                    	builder.append(typeKeySeporator).append(arrayLen);
                    }
                    builder.append(fieldSeporator);
                }
            }
            builder.append(objectSeporator);
        } catch(IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        return builder.toString();
    }

    private int findSequence(Object object) {
        //
        return objectSequenceMap.get(object);
    }

    public Map<Class, LinkedHashSet<Object>> getClassSetMap() {
        //
        return classSetMap;
    }

    private static List<Field> getAllFields(Class clazz) {
    	
    	System.out.println(clazz.getSimpleName());
        List<Field> fields = new ArrayList<Field>();

        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        Class superClass = clazz.getSuperclass();
        if(superClass != null) {
        	//fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
            fields.addAll(getAllFields(superClass));
        }
        System.out.println(fields);

        return fields;
    }
}
