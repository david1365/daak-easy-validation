package da.generality;

import java.text.MessageFormat;

/**
 * Created by Akbari.David on 5/11/2017.
 */
public class StringUtil {
    public static String upperFirst(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String lowerFirst(String str){
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String labelBundleName(String str){
        String strTmp = str.toLowerCase();
        String[] strArr = strTmp.split("_");

        strTmp = "";
        for (String strSplit: strArr){
            strTmp += upperFirst(strSplit);
        }

        return strTmp;
    }

    public static String nvl(Object str){
        return (str == null) ? "" : str.toString();
    }

    public static String removeLast(String str, String removeStr){
       return (nvl(str) == "") ? "" : str.substring(0, str.lastIndexOf(removeStr));
    }

    public static String removeLast(String str){
        return str.substring(0, str.length() -1);
    }

     public static Object[] argToArray(Integer start, Object...objects){
        Integer len = objects.length;
        Object[] resultObjects = new Object[len - start];
        for (int i = start; i < len; i++){
            resultObjects[i - start] = objects[i];
        }

        return resultObjects;
     }

    public static String generateMessage(String message, Object[] parameters){
        return MessageFormat.format(nvl(message), parameters);
    }
}
