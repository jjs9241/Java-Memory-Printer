package javastory.club.stage3.step4.da.map.io;

import javastory.club.stage3.step1.entity.AutoIdEntity;
import javastory.club.stage3.step1.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static javastory.club.stage3.util.DocumentUtil.typeKeySeporator;

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

    public String objectToSimpleString(Object object) {
        //
        String fieldSeporator = ";";
        String keyValueSeporator = " = ";
        String elementSeporator = " , ";

        if(object == null) { return null; }

        StringBuilder builder = new StringBuilder();

        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(object.getClass().getDeclaredFields()));


        try {
            for (Field field: fields) {

                field.setAccessible(true);
                Object fieldObject = field.get(object);
                if (fieldObject != null) {
                    builder.append(field.getName()).append(keyValueSeporator);
                    Class fieldClass = fieldObject.getClass();
                    if (fieldClass.isPrimitive() || (fieldObject instanceof Number && Modifier.isFinal(fieldClass.getModifiers())) || fieldClass == Boolean.class) {
                        builder.append(fieldObject);
                    } else if (fieldClass.getSimpleName().equals("String")) {
                        //스티링처리가 휠씬 어려우니 추가할게 많이 남아있다. ㅠㅠ
                        //builder.append((String) fieldObject);
                        builder.append("\"").append((String) fieldObject).append("\"");
                    } else if (fieldClass.isEnum()) {
                        builder.append(fieldObject);
                    } else if(fieldObject instanceof List) {
                        /*Type genericType = field.getGenericType();
                        if(genericType instanceof ParameterizedType){
                            ParameterizedType pType = (ParameterizedType) genericType;
                            Type[] fieldArgTypes = pType.getActualTypeArguments();
                            for(Type fieldArgType : fieldArgTypes) {
                                Class fieldArgClass = (Class) fieldArgType;
                                builder.append(fieldArgClass.getSimpleName());
                            }
                        }*/
                        builder.append("(");
                        for(Object listElement : (ArrayList)fieldObject){
                            builder.append("\"");
                            if(listElement instanceof Entity) {
                                builder.append(((Entity) listElement).getId());
                            } else if(listElement instanceof AutoIdEntity) {
                                builder.append(((AutoIdEntity) listElement).getId());
                            }
                            builder.append("\"").append(elementSeporator);
                        }
                        builder.append(")");
                    } else {
                        builder.append(findSequence(fieldObject));
                    }
                    builder.append(fieldSeporator);
                }
            }
        } catch(IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        builder.append(objectSeporator);

        return builder.toString();
    }

    public String objectToString(Object object) {
        //
        //String objectSeporator = "#";
        String typeKeySeporator = " : ";
        String fieldSeporator = ";";
        String keyValueSeporator = " = ";

        if(object == null) { return null; }

        StringBuilder builder = new StringBuilder();

        //Field fields[] = object.getClass().getDeclaredFields();
        List<Field> fields = getAllFields(object.getClass());

        if(object.getClass().isArray() && fields.size() == 0) {
            for(int i = 0; i<((Object[])object).length; i++){
                if(((Object[])object)[i] != null) {
                    builder.append(((Object[]) object)[i].getClass().getCanonicalName()).append(typeKeySeporator);
                    builder.append(i).append(keyValueSeporator);
                    Class fieldClass = ((Object[]) object)[i].getClass();
                    if (fieldClass.isPrimitive() || (((Object[]) object)[i] instanceof Number && Modifier.isFinal(fieldClass.getModifiers())) || fieldClass == Boolean.class) {
                        builder.append(((Object[]) object)[i]);
                    } else if (fieldClass.getSimpleName().equals("String")) {
                        //스티링처리가 휠씬 어려우니 추가할게 많이 남아있다. ㅠㅠ
                        builder.append("\"").append((String) ((Object[]) object)[i]).append("\"");
                    } else {
                        builder.append(findSequence(((Object[]) object)[i]));
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
                    if (fieldClass.isPrimitive() || (fieldObject instanceof Number && Modifier.isFinal(fieldClass.getModifiers())) || fieldClass == Boolean.class) {
                        builder.append(fieldObject);
                    } else if (fieldClass.getSimpleName().equals("String")) {
                        //스티링처리가 휠씬 어려우니 추가할게 많이 남아있다. ㅠㅠ
                        builder.append("\"").append((String) fieldObject).append("\"");
                    } else {
                        builder.append(findSequence(fieldObject));
                    }
                    //builder.append(field.getDeclaringClass()).append(keyValueSeporator);
                    //int mod = field.getModifiers();
                    //System.out.println(Modifier.toString(mod));
                    builder.append(fieldSeporator);
                }
            }
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

    public static List<Field> getAllFields(Class clazz) {
        List<Field> fields = new ArrayList<Field>();

        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        Class superClass = clazz.getSuperclass();
        if(superClass != null) {
            fields.addAll(getAllFields(superClass));
        }

        return fields;
    }
}
