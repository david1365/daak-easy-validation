package da.validation;

import da.codes.ExceptionMessageCode;
import da.generality.EasyObject;
import da.dto.ValidateMessageDTO;
import da.enums.ValidationType;

import java.math.BigDecimal;
import java.util.*;

import static da.validation.Validation.*;

/**
 * Created by Akbari.David on 5/8/2017.
 */
@Deprecated
public class BusinessValidation {
    private static final String STRING_REGEX = "^([A-z]+|[\\u0600-\\u06FF\\uFB8A\\u067E\\u0686\\u06AF]+|\\s+)*$";
    private static final String NUMBER_REGEX = "^\\d*$";


//----------------------------------------------------
    @Deprecated
    public static ValidateMessageDTO validateCollection(Collection<?> collection) {
        Boolean valid = collection != null && !collection.isEmpty();
        String validationType = valid ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_COLLECTION_NULL;

        return new ValidateMessageDTO(validationType, ExceptionMessageCode.GENERAL_LIST_IS_EMPTY);
    }
//------------------------------------------------
    public static ValidateMessageDTO checkUnique(Collection<?> entities, Object fieldName){
        Map<String, Integer> counted = new HashMap<>();

        for(Object entity: entities){
            String key = EasyObject.getMethodValueByFieldName(entity, fieldName.toString()).toString();

            if (counted.get(key) == null){
                counted.put(key, 1);
            }
            else{
                return validateMessage(ValidationType.GENERAL_CHECK_UNIQUE,
                        ExceptionMessageCode.GENERAL_VALUE_OF_ENTITY_IS_DUPLICATED);
            }
        }

        return validateMessage(ValidationType.GENERAL_VALID);
    }
//---------------------------------------------------------------------------------------------------
    @Deprecated
    public static <T> ValidateMessageDTO validateNotNull(T object){
        Boolean valid = object != null;
        String validationType = valid ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_OBJECT_NULL;

        return new ValidateMessageDTO(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NULL);
    }
//-----------------------------------------------------------
//    public static ValidateMessageDTO validateNotNull(Object object){
//        Boolean valid = object != null;
//        String validationType = valid ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_OBJECT_NULL;
//
//        return new ValidateMessageDTO(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NULL);
//    }
//----------------------------------------------------------------------------------------------

    public static boolean validateList(List ls) {
        return ls != null && !ls.isEmpty();
    }

    public static boolean validateSet(Set ls) {
        return ls != null && !ls.isEmpty();
    }

//    public static boolean validateFanapHashSet(FanapPersistHashSet ls) {
//        return ls != null && !ls.isEmpty();
//    }

    public static boolean validateSLong(long val) {
        return (val != 0);
    }

    public static boolean validateLong(Long val) {
        return (val != null);
    }

    public static boolean validateBigDecimal(BigDecimal val) {
        return (val != null);
    }
//-----------------------------------------------------------------------
    public static ValidateMessageDTO validateString(String str) {
        Boolean valid = (str != null && !str.trim().isEmpty());

        String validationType = valid ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_STRING_NULL;

        return validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NULL);
    }
//-----------------------------------------------------------------
    public static boolean validateSInteger(int val) {
        return (val != 0);
    }

    public static boolean validateInteger(Integer val) {
        return (val != null);
    }

    public static boolean validateSFloat(float val) {
        return (val != 0);
    }

    public static boolean validateFloat(Float val) {
        return (val != null);
    }

    public static boolean validatePercent(Integer val) {
        return (val > 0 && val < 100);
    }

    @SuppressWarnings("Duplicates")
    public static Boolean detectBoolean(int i) {
        if (i == 1)
            return true;
        else if (i == 0)
            return false;
        else
            return null;
    }

    public static boolean validateBoolean(Boolean bool) {
        return bool != null;
    }

    public static boolean equalString(String str1, String str2) {
        String s1 = replaceNullWithEmptyString(str1);
        String s2 = replaceNullWithEmptyString(str2);
        return s1.equals(s2);
    }

    private static String replaceNullWithEmptyString(String str) {
        return str == null ? "" : str.trim();
    }

    public static boolean compareValidDates(String dateFrom, String dateTo) {
        return Integer.valueOf(dateTo.replace("/", "")) >= Integer.valueOf(dateFrom.replace("/", ""));
    }

    public static boolean isNotText(String value) {
        return !value.matches(STRING_REGEX);
    }

    public static ValidateMessageDTO isNumber(String value) {
        Boolean valid = value.matches(NUMBER_REGEX);

        String validationType = valid ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_IS_NO_NUMBER;

        return validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NULL);
    }

}