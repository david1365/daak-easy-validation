package da.generality;

import da.enums.MessageCode;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

//import static da.validation.Validation.*;

/**
 * Created by Akbari.David on 5/11/2017.
 */
public class EasyObject {
    private static final String GETTER_PREFIX_METHOD = "get";
    private static final String SETTER_PREFIX_METHOD = "set";
    private static final String[] primitive = {
            boolean.class.getTypeName(),
            byte.class.getTypeName(),
            char.class.getTypeName(),
            short.class.getTypeName(),
            int.class.getTypeName(),
            long.class.getTypeName(),
            float.class.getTypeName(),
            double.class.getTypeName()
    };

    public static Boolean isPrimitive(Class classType){
        String typeName = classType.getTypeName();
        return Arrays.stream(primitive).anyMatch(typeName::equals);
    }

    public static Class<?> getEntity(Object object) {
        //String tableName = "";
        Class<?> entityClass = null;

        try {
            String className = object.getClass().getName();
            className = className.substring(0, className.indexOf("$"));

            entityClass = Class.forName(className);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return entityClass;
    }

    public static Field getField(Class<?> entityClass, String fieldName){
        Field field = null;
        try {
            field = entityClass.getField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return field;
    }

    public static Field getDeclaredField(Class<?> entityClass, String fieldName){
        Field field = null;
        try {
            field = entityClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return  field;
    }

    public static Boolean isCorrectField(Class<?> entityClass, String fieldName){
        for (Field field: entityClass.getDeclaredFields()){
            if (fieldName.equals(field.getName())){
               return true;
            }
        }

        return false;
    }

    public static Field getBestDeclaredField(Class<?> entityClass, String fieldName, Class entityParentClass){
        if(isCorrectField(entityClass, fieldName)){
            return getDeclaredField(entityClass, fieldName);
        }

        return getDeclaredField(entityParentClass, fieldName);
    }

    public static String getterMethodName(String fieldName){
        return GETTER_PREFIX_METHOD.concat(StringUtil.upperFirst(fieldName));
    }

    public static Method getGetterMethod(Class clazz, String fieldName){
        return getMethod(clazz, getterMethodName(fieldName));
    }

    public static Method getGetterMethod(Object object, String fieldName){
        Class clazz = object.getClass();
        return getMethod(clazz, getterMethodName(fieldName));
    }

    public static Method getMethod(Class clazz, String methodName, Class...parametersTyp){
        Method method = null;
        try {
            method = clazz.getMethod(methodName, parametersTyp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return  method;
    }

    public static Method getMethod(Object object, String methodName, Class...parametersTyp){
        Class clazz = object.getClass();
        return getMethod(clazz, methodName, parametersTyp);
    }

    public static String setterMethodName(String fieldName){
        return SETTER_PREFIX_METHOD.concat(StringUtil.upperFirst(fieldName));
    }

    public static Method getSetterMethod(Class clazz, String fieldName, Class...parametersTyp){
        return getMethod(clazz, setterMethodName(fieldName), parametersTyp);
    }

    public static Method getSetterMethod(Object object, String fieldName, Class...parametersTyp){
        return getMethod(object, setterMethodName(fieldName), parametersTyp);
    }

    public static void setMethodValue(Object object, Method method, Object...values){
        try {
             method.invoke(object, values);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

//    public static void setSetterMethodValue(Object...values){
//        validation().val(values.length).min(3).throwValidateMessage(MessageCode.GENERAL_COUNT_OF_PARAMETER_IS_LOW)
//                    .isNotNull(values[0]).throwValidateMessage(MessageCode.GENERAL_OBJECT_IS_NULL)
//                    .andValue(values[1]).throwValidateMessage(MessageCode.GENERAL_FIELD_NAME_IS_NULL);
//
//        Object object = values[0];
//        String fieldName = values[1].toString();
//        Object realValue = values[2];
//
//        Class classType = getFieldType(object.getClass(), fieldName);
//        Method method = getSetterMethod(object, fieldName, classType);
//        realValue = isPrimitive(classType) ? realValue : classType.cast(realValue);
//        setMethodValue(object, method, realValue);
//    }

    public static Class getFieldType(Class classObject , String fieldName){
        for (Field field: classObject.getDeclaredFields()){
            if (fieldName.equals(field.getName())){
                return field.getType();
            }
        }

        Class superclass = classObject.getSuperclass();
        if (superclass == null){
            return null;
        }

        return getFieldType(superclass, fieldName);
    }

    public static Object getMethodValueByFieldName(Object object, String fieldName, Class<?> ... parameters) {
        Method getterMethod = getGetterMethod(object, fieldName);
        return getMethodValue(object, getterMethod, parameters);
    }

    public static Object getMethodValue(Object object, Method method, Class<?> ... parameters) {
        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public static Object getMethodValue(Object object, String methodName, Class<?> ... parameters) {
        Method method = getMethod(object, methodName);
        return getMethodValue(object, method, parameters);
    }

    public static String generateKey(Object object, String ... fieldNames) {
        String key = "";

        for (String fieldName: fieldNames){
            key += EasyObject.getMethodValueByFieldName(object, fieldName);
        }

        return key;
    }

    public static <T> T newInstance(Class<T> entityClass) {
        T newEntity = null;

        try {
            newEntity = entityClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return newEntity;
    }

    public static Boolean findMethod(Class clazz, String name) {
        Method[] methods = clazz.getMethods();
        return Arrays.stream(methods).map((inMethod)-> inMethod.getName()).anyMatch(name::equals);
    }

    public static String obtainFieldName(String methodName) {
        String newStr = methodName.substring(3, methodName.length());
        return StringUtil.lowerFirst(newStr);
    }

    public static String negateSetterGetterName(String methodName){
        String newStr = methodName.substring(3, methodName.length());
        return (methodName.startsWith(EasyObject.GETTER_PREFIX_METHOD) ? EasyObject.SETTER_PREFIX_METHOD : EasyObject.GETTER_PREFIX_METHOD).concat(newStr);
    }

    public static Boolean isGetter(String methodName) {
        return methodName.startsWith(EasyObject.GETTER_PREFIX_METHOD);
    }

    public static Boolean isSetter(String methodName) {
        return methodName.startsWith(EasyObject.SETTER_PREFIX_METHOD);
    }

//    public static <T> T unify(Object sourceObject, T destinationObject) {
//        Class sourceClass = sourceObject.getClass();
//        Class destinationClass = destinationObject.getClass();
//
//        Method[] methods = sourceClass.getMethods();
//        for (Method method: methods){
//            String methodName = method.getName();
//            if(isGetter(methodName)) {
//                String setterName = negateSetterGetterName(methodName);
//                if (findMethod(destinationClass, setterName)) {
//                    Object getterValue = getMethodValue(sourceObject, methodName);
//                    String fieldName = obtainFieldName(setterName);
//                    setSetterMethodValue(destinationObject, fieldName, getterValue);
//                }
//            }
//        }
//
//        return destinationObject;
//    }


}
