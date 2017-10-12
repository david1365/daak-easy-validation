package da.validation;

import da.status.MethodManager;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by d.akbari on 6/19/2017.
 */
@Deprecated
public class ValidationManager extends MethodManager {
    public static ValidationManager validationManager(){
        ValidationManager me = new ValidationManager();

        return me;
    }

    public <T> ValidationManager addValidation(String validationType, Consumer<T> consumerMethod){
        methodLists.put(validationType, consumerMethod);

        return this;
    }

    public <T, R> ValidationManager addValidation(String validationType, Function<T, R> functionMethod){
        methodLists.put(validationType, functionMethod);

        return this;
    }

    public <T, U, R> ValidationManager addValidation(String validationType, BiFunction<T, U, R> functionMethod){
        methodLists.put(validationType, functionMethod);

        return this;
    }

    //------------------------------------------------------------
    public <T, R> R executeValidation(String validationType, T value){
        return run(validationType, value, null);
    }

    public  <T, U, R> R executeValidation(String validationType, T value, U value2){
        return run(validationType, value, value2);
    }
}
