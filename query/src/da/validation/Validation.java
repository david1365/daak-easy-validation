package da.validation;

import com.google.gson.Gson;
import da.codes.ExceptionMessageCode;
import da.generality.EasyObject;
import da.generality.StringUtil;
import da.dto.ValidateMessageDTO;
import da.enums.ValidationType;
import da.interfaces.MuFunction;
import da.interfaces.MuFunctionValidation;
import da.status.MethodManager;
import da.exception.ProcessException;
//import com.google.gson.Gson;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Akbari.David on 5/9/2017.
 */
public class Validation extends MethodManager {
    //TODO: remove static from this fields if need
    private static final String STRING_REGEX = "^([A-z]+|[\\u0600-\\u06FF\\uFB8A\\u067E\\u0686\\u06AF]+|\\s+)*$";
    private static final String NUMBER_REGEX = "^\\d*$";

    protected Map<String, Class> methodParameterTypeLists = new HashMap<>();

    private final String HTML_BR = "<br>";
    private final String STAR = "*";
    private final String SPACE = " ";

    private Object[] values = null;
    private ValidateMessageDTO validateMessage = null;
    private List<ValidateMessageDTO> validateMessageList = new ArrayList<>();
    private Integer messageIndex = 0;
    private Object ifResult = null;
    private Boolean stopValidate = false;

    private Map<Object, Integer> uniqe = new HashMap<>();

    private Object previousValidation;

    private Validation() {}

    public static Validation validation() {
        return new Validation();
    }

    private ValidateMessageDTO getValidateMessage() {
        return validateMessage;
    }

    private void setValidateMessage(ValidateMessageDTO validateMessage) {
        this.validateMessage = validateMessage;

        this.getValidateMessageList().add(this.messageIndex, validateMessage);
        this.messageIndex++;

    }

    private List<ValidateMessageDTO> getValidateMessageList() {
        return validateMessageList;
    }

    private void setValidateMessageList(List<ValidateMessageDTO> validateMessageList) {
        this.validateMessageList = validateMessageList;
    }

    private Object getPreviousValidation() {
        return previousValidation;
    }

    private void setPreviousValidation(Object previousValidation) {
        this.previousValidation = previousValidation;
    }

    private void setValues(Object...values) {
        int count = values != null ? values.length : 1;
        this.values = new Object[count];

        if(values == null) {
            return;
        }

        for (int i = 0; i < values.length; i++) {
            this.values[i] = values[i];
        }
    }

    public Validation setAsValue() {
        this.val(this.ifResult);
        return this;
    }

    //TODO: resolve setvalues and val ... only one of them
    public <T>Validation val(T...values) {
        setValues(values);

        return this;
    }
//-----------------------------------------------------------------------------------------------
    private <T> ValidateMessageDTO logicalValidate(T...inputValues){
        Object previousValidation = this.getPreviousValidation();

        if (previousValidation instanceof Supplier){
            return validation().val(inputValues).validate((Supplier) previousValidation).take();
        }
        else if (previousValidation instanceof MuFunctionValidation) {
            return validation().val(inputValues).validate((MuFunctionValidation) previousValidation).take();
        }
        else if (previousValidation instanceof  Consumer){
            return validation().val(inputValues).validate((Consumer) previousValidation).take();
        }

        return null;
    }



    private <T> Validation andDecision(ValidateMessageDTO inputValidateMessage) {
        if(!inputValidateMessage.isValid()){
            this.setValidateMessage(inputValidateMessage);
        }

        return this;
    }

    public <T> Validation andValue(T...values) {
        setValues(values);

        ValidateMessageDTO andValidateMessage = logicalValidate(values);

        return andDecision(andValidateMessage);
    }

    public  <T> Validation andValidate(Consumer<T> validation){
        return andDecision(validation().val(this.values).validate(validation).take());
    }

    public Validation andValidate(Supplier<ValidateMessageDTO> validation){
        return andDecision(validation().val(this.values).validate(validation).take());
    }

    public <T> Validation andValidate(MuFunctionValidation validation){
        return andDecision(validation().val(this.values).validate(validation).take());
    }

    public  <T> Validation andNegateValidate(Consumer<T> validation){
        return andDecision(validation().val(this.values).validate(validation).negateTake());
    }

    public Validation andNegateValidate(Supplier<ValidateMessageDTO> validation){
        return andDecision(validation().val(this.values).validate(validation).negateTake());
    }

    public <T> Validation andNegateValidate(MuFunctionValidation validation){
        return andDecision(validation().val(this.values).validate(validation).negateTake());
    }

    //=================================================================
    private <T> Validation orDecision(ValidateMessageDTO inputValidateMessage) {
        if(inputValidateMessage.isValid()){
            this.setValidateMessage(inputValidateMessage);
        }

        return this;
    }

    public <T> Validation orValue(T...values) {
        setValues(values);

        ValidateMessageDTO orValidateMessage = logicalValidate(values);

        return orDecision(orValidateMessage);
    }

    public  <T> Validation orValidate(Consumer<T> validation){
        return orDecision(validation().val(this.values).validate(validation).take());
    }

    public Validation orValidate(Supplier<ValidateMessageDTO> validation){
        return orDecision(validation().val(this.values).validate(validation).take());
    }

    public <T> Validation orValidate(MuFunctionValidation validation){
        return orDecision(validation().val(this.values).validate(validation).take());
    }

    public  <T> Validation orNegateValidate(Consumer<T> validation){
        return orDecision(validation().val(this.values).validate(validation).negateTake());
    }

    public Validation orNegateValidate(Supplier<ValidateMessageDTO> validation){
        return orDecision(validation().val(this.values).validate(validation).negateTake());
    }

    public <T> Validation orNegateValidate(MuFunctionValidation validation){
        return orDecision(validation().val(this.values).validate(validation).negateTake());
    }

//    public <T> Validation orNegateValidate(FunctionValidation<T> validation){
//        return orDecision(validation().val(this.value).validate(validation).negateTake());
//    }
//
//    public <T, U> Validation orNegateValidate(BiFunctionValidation <T, U> validation){
//        return orDecision(validation().val(this.value, this.value2).validate(validation).negateTake());
//    }
    //----------------------------------------------------------


    private Validation runValidation(Supplier<ValidateMessageDTO> startValidation){
        if (!this.stopValidate){
            this.setValidateMessage(startValidation.get());
        }

        return this;
    }

    public Validation validate(Consumer validation){
        this.setPreviousValidation(validation);

        return runValidation(()->{
            validation.accept(this.values);
            return null;
        });
    }

    public Validation validate(Supplier<ValidateMessageDTO> validation){
        this.setPreviousValidation(validation);
        return runValidation(()-> validation.get());
    }

    public <T> Validation validate(MuFunctionValidation validation){
        this.setPreviousValidation(validation);

        return runValidation(()-> validation.applys(this.values));
    }

    public <T> ValidateMessageDTO take(MuFunctionValidation validation){
        return this.validate(validation).take();
    }

    public ValidateMessageDTO negateTake(){
        return this.getValidateMessage().isValid() ? notValid() : valid();
    }

    public ValidateMessageDTO take(){
        return this.getValidateMessage();
    }
//--------------------------------------------------------------------
    public <T> Validation ifValid(){
        stopValidate = !getValidateMessage().isValid();

        return  this;
    }

    private Validation runIfValid(Supplier<Object> startIfValid){
        if (this.ifValid().stopValidate == false) {
            this.ifResult = startIfValid.get();
        }

        return this;
    }

    public <T> Validation ifValid(Consumer method){
        return runIfValid(()-> {
            method.accept(this.values);
            return null;
        });
    }

    public <T> Validation ifValid(Supplier<T> method){
       return runIfValid(()-> method.get());
    }

    public <T> Validation ifValid(T result){
        return runIfValid(()->  result);
    }

    public <R> Validation ifValid(MuFunction<R> method){
        return runIfValid(()-> method.applys(this.values));
    }
    public Validation ifValid(Runnable method){
        return runIfValid(()-> {
            method.run();
            return null;
        });
    }
//--------------------------------------------------------------------
    private Validation runIfNotValid(Supplier<Object> startIfNotValid){
        if (this.ifValid().stopValidate){
            this.ifResult = startIfNotValid.get();
            this.stopValidate = false;
        }

        return this;
    }

    public <T> Validation ifNotValid(Supplier<T> method){
        return runIfNotValid(()->  method.get());
    }

    public <T> Validation ifNotValid(T result){
        return runIfNotValid(()->  result);
    }

    public <T> Validation ifNotValid(){
        return runIfNotValid(()->  0);
    }

    public <T> Validation ifNotValid(Consumer method){
        return runIfNotValid(()-> {
            method.accept(this.values);
            return null;
        });
    }

    public <R> Validation ifNotValid(MuFunction<R> method){
        return runIfNotValid(()-> method.applys(this.values));
    }

    public Validation ifNotValid(Runnable method){
        return runIfNotValid(()-> {
                method.run();
                return null;
        });
    }

    public Boolean isValid(){
        return getValidateMessage().isValid();//!ifValid().stopValidate;
    }

    @Deprecated
    public Validation ifNotValidSetAsResult(){
        return runIfNotValid(()-> this.getValidateMessage());
    }

//    public <T> Boolean isValid(FunctionValidation<T> validation) {
//        if (me.validate(validation).validateMessage.getValidationType() == ValidationType.GENERAL_VALID) {
//            return  true;
//        }
//
//        //nullValue();
//
//        return  false;
//    }
//
//    public <T, U> Boolean isValid(BiFunctionValidation <T, U> validation) {
//        if (me.validate(validation).validateMessage.getValidationType() == ValidationType.GENERAL_VALID) {
//            return  true;
//        }
//
//        //nullValue();
//
//        return  false;
//    }

//--public method--------------------------------------------------------------------------------------------

    public <T> Validation isNotNull(T...values){
        setValues(values);

        return validate((inValues)-> {
            for (Object value: inValues){
                if (value == null){
                    return validateMessage(ValidationType.GENERAL_OBJECT_NULL, ExceptionMessageCode.GENERAL_VALUE_IS_NULL);
                }
            }

            return validateMessage(ValidationType.GENERAL_VALID);
        });
    }

    public Validation isNotNull() {
        return this.isNotNull(this.values);
    }

    public <T> Validation isNull(T...values){
        setValues(values);

        return validate((inValues)-> {
            for (Object value: inValues){
                if (value != null) {
                    return validateMessage(ValidationType.GENERAL_OBJECT_NOT_NULL, ExceptionMessageCode.GENERAL_VALUE_IS_NOT_NULL);
                }
            }

            return validateMessage(ValidationType.GENERAL_VALID);
        });
    }
    public Validation isNull() {
        return this.isNull(this.values);
    }

    public <T> Validation itemsIsNotNull(Collection...collections) {
        setValues(collections);

        return validate((inCollections)-> {
            for(Object collection: inCollections){
                for(Object entity: (Collection)collection){
                    if (entity == null) {
                        return validateMessage(ValidationType.GENERAL_COLLECTION_VALUE_NULL,
                                ExceptionMessageCode.GENERAL_VALUE_OF_LIST_IS_EMPTY);
                    }
                }
            }

            return validateMessage(ValidationType.GENERAL_VALID);
        });
    }

    public Validation collectionsIsEmpty(Collection...collections) {
        setValues(collections);

        return validate((inCollections)-> {
            return validation().isNotNull(inCollections)
                        .ifValid((notNullCollections)->{
                            for(Object collection: notNullCollections){
                                if(((Collection) collection).isEmpty()){
                                    return validateMessage(ValidationType.GENERAL_COLLECTION_NULL
                                                , ExceptionMessageCode.GENERAL_LIST_IS_EMPTY);
                                }
                            }

                            return validateMessage(ValidationType.GENERAL_VALID);
                        })
                        .get();

        });
    }

//    public Validation collectionIsEmpty() {
//        return this.collectionIsEmpty(this.values);
//    }

//    public Validation collectionSingleRow(Collection collection) {
//        setValue(collection);
//
//        return validate((inCollection)-> {
//            return validation().isNotNull(inCollection)
//                    .<Collection, ValidateMessageDTO>ifValid((notNullCollection)->{
//                            String validationType = notNullCollection.size() != 1 ? ValidationType.GENERAL_COLLECTION_DUPLICATED : ValidationType.GENERAL_VALID;
//
//                            return new ValidateMessageDTO(validationType, ExceptionMessageCode.GENERAL_COLLECTION_DUPLICATED_ROW);
//                    })
//                    .get();
//        });
//    }
//
//    public Validation collectionSingleRow() {
//        return this.collectionSingleRow((Collection) this.value);
//    }
//
//    public <T> Validation checkUnique(Collection<T> entities, Object fieldName) {
//        setValue(entities, fieldName);
//
//        Map<String, Integer> counted = new HashMap<>();
//
//        return validate((inEntities, inFieldName)-> {
//            validation().collectionIsEmpty((Collection) inEntities).throwValidateMessage(ExceptionMessageCode.GENERAL_LIST_IS_EMPTY_OR_NOT_CORRECT);
//
//            for(Object entity: (Collection)inEntities){
//                validation().isNotNull(entity).throwValidateMessage(ExceptionMessageCode.GENERAL_VALUE_OF_LIST_IS_EMPTY);
//
//                Object value = EasyObject.getMethodValueByFieldName(entity, fieldName.toString());
//
//                String key = value.toString();
//                if (counted.get(key) == null){
//                    counted.put(key, 1);
//                }
//                else{
//                    return validateMessage(ValidationType.GENERAL_CHECK_UNIQUE,
//                            ExceptionMessageCode.GENERAL_VALUE_OF_ENTITY_IS_DUPLICATED);
//                }
//            }
//
//            return validateMessage(ValidationType.GENERAL_VALID);
//        });
//    }
//
//    public Validation checkUniqueValue(Object value){
//        setValue(value);
//
//        return validate((inValue)-> {
//            String key = inValue.toString();
//            if (uniqe.get(key) == null){
//                uniqe.put(key, 1);
//            }
//            else{
//                uniqe.clear();
//                return validateMessage(ValidationType.GENERAL_CHECK_UNIQUE,
//                        ExceptionMessageCode.GENERAL_VALUE_OF_ENTITY_IS_DUPLICATED);
//            }
//
//            return valid();
//        });
//    }
//
//    public Validation checkUniqueValue(){
//        return this.checkUniqueValue(this.value);
//    }
//
//    public  Validation checkUnique(){
//       return this.checkUnique((Collection) this.value, this.value2);
//    }
//
//    public  Validation checkUnique(Object fieldName){
//        return this.checkUnique((Collection) this.value, fieldName);
//    }
////--------------------------------------------
//    public Validation isText(Object value) {
//        setValue(value);
//
//        return validate((inValue)-> {
//            return validation().isNotNull(inValue)
//                        .ifValid((notNullValue)->{
//                                String validationType = notNullValue.toString().matches(STRING_REGEX) ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_IS_NO_TEXT;
//
//                                return new ValidateMessageDTO(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NO_TEXT);
//                        })
//                        .get();
//        });
//    }
//
//    public Validation isText() {
//        return this.isText(this.value.toString());
//    }
//
//    public Validation isNumber(Object value) {
//        setValue(value);
//
//        return validate((inValue)-> {
//            return validation().isNotNull(inValue)
//                        .ifValid((notNullValue)->{
//                                String validationType = notNullValue.toString().matches(NUMBER_REGEX) ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_IS_NO_NUMBER;
//
//                                return new ValidateMessageDTO(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NO_NUMBER);
//                        })
//                        .get();
//        });
//    }
//
//    public Validation isNumber() {
//        return this.isNumber(this.value);
//    }
//
//    public Validation isBoolean(Object value) {
//        setValue(value);
//
//        return validate((inValue)-> {
//            return validation().isNotNull(inValue)
//                        .ifValid((notNullValue)->{
//                                String validationType = (notNullValue.toString() == "true") || (value.toString() =="false") ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_IS_NO_BOOLEAN;
//
//                                return new ValidateMessageDTO(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NO_BOOLEAN);
//                        })
//                        .get();
//        });
//    }
//
//    public Validation isBoolean() {
//        return this.isBoolean(this.value);
//    }
//
//    public Validation isTrue(Boolean value) {
//        setValue(value);
//
//        return validate((inValue)-> {
//           return validation().isNotNull(inValue)
//                    .<Boolean,ValidateMessageDTO>ifValid((notNullValue)->{
//
//                            String validationType = notNullValue ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_IS_FALSE;
//
//                            return validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_FALSE);
//                    })
//                   .get();
//        });
//    }
//
//    public Validation isTrue() {
//        return this.isTrue((Boolean) this.value);
//    }
//
//    private <T extends Number & Comparable<? super T>> void setBestValue(T compareValue){
//        this.value2 = this.value;
//        this.value = compareValue;
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation max(T compareValue, T value) {
//        setValue(compareValue, value);
//
//        return validate((inCompareValue, inValue)-> {
//            return validation().isNotNull(inCompareValue, inValue)
//                    .<T, T, ValidateMessageDTO>ifValid((notNullCompareValue, notNullValue)->{
//                        Boolean maxCheck = (notNullValue.compareTo(notNullCompareValue) > 0);
//
//                        String  validationType =  maxCheck ? ValidationType.GENERAL_MAX : ValidationType.GENERAL_VALID;
//
//                        return  validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_MAX);
//                    })
//                    .get();
//        });
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation max(T compareValue) {
//        setBestValue(compareValue);
//
//        return this.max((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation max() {
//        return this.max((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation min(T compareValue, T value) {
//        setValue(compareValue, value);
//
//        return validate((inCompareValue, inValue)-> {
//            return validation().isNotNull(inCompareValue, inValue)
//                    .<T, T, ValidateMessageDTO>ifValid((notNullCompareValue, notNullValue)->{
//                        Boolean minCheck = (notNullValue.compareTo(notNullCompareValue) < 0);
//
//                        String  validationType = minCheck ? ValidationType.GENERAL_MIN : ValidationType.GENERAL_VALID;
//
//                        return  validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_MIN);
//                    })
//                    .get();
//        });
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation min(T compareValue) {
//        setBestValue(compareValue);
//
//        return this.min((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation min() {
//        return this.min((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation equals(T compareValue, T value) {
//        setValue(compareValue, value);
//
//        return validate((inCompareValue, inValue)-> {
//            return validation().isNotNull(inCompareValue, inValue)
//                    .<T, T, ValidateMessageDTO>ifValid((notNullCompareValue, notNullValue)->{
//                        Boolean checkEquals = (notNullValue.compareTo(notNullCompareValue) == 0);
//
//                        String  validationType = checkEquals ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_NOT_EQUAL;
//
//                        return  validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NOT_EQUAL);
//                    })
//                    .get();
//        });
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation equals(T compareValue) {
//        setBestValue(compareValue);
//
//        return this.equals((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation equals() {
//        return this.equals((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation isNotEquals(T compareValue, T value) {
//        setValue(compareValue, value);
//
//        return validate((inCompareValue, inValue)-> {
//            return validation().isNotNull(inCompareValue, inValue)
//                    .<T, T, ValidateMessageDTO>ifValid((notNullCompareValue, notNullValue)->{
//                        Boolean checkisNotEquals = (notNullValue.compareTo(notNullCompareValue) != 0);
//
//                        String  validationType = checkisNotEquals ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_EQUAL;
//
//                        return  validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_EQUAL);
//                    })
//                    .get();
//        });
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation isNotEquals(T compareValue) {
//        setBestValue(compareValue);
//
//        return this.isNotEquals((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation isNotEquals() {
//        return this.isNotEquals((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation greaterThanOrEquals(T compareValue, T value) {
//        setValue(compareValue, value);
//
//        return validate((inCompareValue, inValue)-> {
//            return validation().isNotNull(inCompareValue, inValue)
//                    .<T, T, ValidateMessageDTO>ifValid((notNullCompareValue, notNullValue)->{
//                        Boolean checkGreaterThanOrEqual = (notNullValue.compareTo(notNullCompareValue) == 0) || (notNullValue.compareTo(notNullCompareValue) > 0);
//
//                        String  validationType = checkGreaterThanOrEqual ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_NOT_GREATER_THAN_OR_EQUAL;
//
//                        return  validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NOT_GREATER_THAN_OR_EQUAL);
//                    })
//                    .get();
//        });
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation greaterThanOrEquals(T compareValue) {
//        setBestValue(compareValue);
//
//        return this.greaterThanOrEquals((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation greaterThanOrEquals() {
//        return this.greaterThanOrEquals((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation greaterThan(T compareValue, T value) {
//        setValue(compareValue, value);
//
//        return validate((inCompareValue, inValue)-> {
//            return validation().isNotNull(inCompareValue, inValue)
//                    .<T, T, ValidateMessageDTO>ifValid((notNullCompareValue, notNullValue)->{
//
//                        String  validationType = (notNullValue.compareTo(notNullCompareValue) > 0) ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_NOT_GREATER_THAN;
//
//                        return  validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NOT_GREATER_THAN);
//                    })
//                    .get();
//        });
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation greaterThan(T compareValue) {
//        setBestValue(compareValue);
//
//        return this.greaterThan((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation greaterThan() {
//        return this.greaterThan((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation lessThanOrEquals(T compareValue, T value) {
//        setValue(compareValue, value);
//
//        return validate((inCompareValue, inValue)-> {
//            return validation().isNotNull(inCompareValue, inValue)
//                    .<T, T, ValidateMessageDTO>ifValid((notNullCompareValue, notNullValue)->{
//                        Boolean checklessThanOrEqual = (notNullValue.compareTo(notNullCompareValue) == 0) || (notNullValue.compareTo(notNullCompareValue) < 0);
//
//                        String  validationType = checklessThanOrEqual ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_NOT_LESS_THAN_OR_EQUAL;
//
//                        return  validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NOT_LESS_THAN_OR_EQUAL);
//                    })
//                    .get();
//        });
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation lessThanOrEquals(T compareValue) {
//        setBestValue(compareValue);
//
//        return this.lessThanOrEquals((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation lessThanOrEquals() {
//        return this.lessThanOrEquals((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation lessThan(T compareValue, T value) {
//        setValue(compareValue, value);
//
//        return validate((inCompareValue, inValue)-> {
//            return validation().isNotNull(inCompareValue, inValue)
//                    .<T, T, ValidateMessageDTO>ifValid((notNullCompareValue, notNullValue)->{
//
//                        String  validationType = (notNullValue.compareTo(notNullCompareValue) < 0) ? ValidationType.GENERAL_VALID : ValidationType.GENERAL_NOT_LESS_THAN;
//
//                        return  validateMessage(validationType, ExceptionMessageCode.GENERAL_VALUE_IS_NOT_LESS_THAN);
//                    })
//                    .get();
//        });
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation lessThan(T compareValue) {
//        setBestValue(compareValue);
//
//        return this.lessThan((T) this.value, (T) this.value2);
//    }
//
//    public <T extends Number & Comparable<? super T>> Validation lessThan() {
//        return this.lessThan((T) this.value, (T) this.value2);
//    }

    public <T> T getIfResult() {
        return (T) this.ifResult;
    }

    public <T> T get() {
        if (this.ifResult == null){
            this.ifResult = this.getValidateMessage();
        }

        return (T) this.ifResult;
    }
    private void throwProcessException(String exceptionMessageCode, Object... parameters) throws ProcessException {
        throw new ProcessException(exceptionMessageCode, parameters);
    }

    private void throwProcessException(Throwable exception) {
        throw new ProcessException(exception.getMessage());
    }

    public Validation throwValidate(String... parameters){
       return throwValidateMessage(getValidateMessage().getMessage(), parameters);
    }

    public Validation throwValidateMessage(String exceptionMessageCode, Object... parameters){
        parameters = parameters;

        if (!stopValidate){
            if (!getValidateMessage().isValid()) {
                uniqe.clear();
                throwProcessException(exceptionMessageCode, parameters);
            }
        }

        return this;
    }

    public Validation throwValidate(){
       return throwValidateMessage(getValidateMessage().getMessage());
    }

    public Validation throwAllValidate() {
        String message = generateValidateMessages();
        throwValidateMessage(message);

        return this;
    }

    public <T> Validation throwValidate(MuFunctionValidation validation){
        return this.validate(validation).throwValidate();
    }

    public Validation throwValidate(Supplier<ValidateMessageDTO> validation) {
        return this.validate(validation).throwValidate();
    }

    public Validation setThrowBundles(Object... parameters) {
        this.getValidateMessageList().get(this.messageIndex - 1).setParameters(parameters);

        return this;
    }

    public Validation setThrowMessage(String exceptionMessageCode, Object... parameters) {
        Integer index = this.messageIndex - 1;
        this.getValidateMessageList().get(index).setMessage(exceptionMessageCode);
        this.getValidateMessageList().get(index).setParameters(parameters);

        return this;
    }

    private String generateValidateMessages() {
        String message = "";
        Boolean notValid = false;
        for(ValidateMessageDTO validateMessage: this.getValidateMessageList()){
            if(!validateMessage.isValid()) {
                notValid = true;
                if (validateMessage.getMessage() != null) {
                    message += STAR + SPACE + validateMessage.getMessage() + HTML_BR + HTML_BR;
                }
            }
        }

        if(notValid){
            this.setValidateMessage(notValid());
        }

        return StringUtil.removeLast(message, HTML_BR + HTML_BR);
    }

    public Validation putValidation(String validationType, Object method, Class valueClass){
        methodLists.put(validationType, method);
        methodParameterTypeLists.put(validationType, valueClass);

        return this;
    }

    public <T, R> Validation addValidation(String validationType, Function<T, R> functionMethod, Class valueClass){
        return putValidation(validationType, functionMethod, valueClass);
    }

    public ValidateMessageDTO executeValidation(String validationType, String value){
        Gson gson = new Gson();
        Class valueClass =  methodParameterTypeLists.get(validationType);

        validation().isNotNull(valueClass).throwValidateMessage(ExceptionMessageCode.GENERAL_NOT_THE_METHOD_NAME);

        Object realValue = gson.fromJson(value,  valueClass);

        return run(validationType, realValue);
    }

    public static ValidateMessageDTO validateMessage(String validationType) {
        return new ValidateMessageDTO(validationType);
    }

    public static ValidateMessageDTO validateMessage(String validationType, String message) {
        return new ValidateMessageDTO(validationType, message);
    }

    public static ValidateMessageDTO validateMessage(String validationType, String message, Object... parameters) {
        return new ValidateMessageDTO(validationType, message, parameters);
    }

    public static ValidateMessageDTO valid() {
        return validateMessage(ValidationType.GENERAL_VALID);
    }

    public static ValidateMessageDTO notValid() {
        return validateMessage(ValidationType.GENERAL_NOT_VALID);
    }
}
