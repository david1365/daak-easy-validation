package da.status;

import da.codes.ExceptionMessageCode;

import static da.validation.Validation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by d.akbari on 6/19/2017.
 */
public abstract class MethodManager {
    protected Map<String, Object> methodLists = new HashMap<>();

    public Map<String, Object> getMethodLists() {
        return methodLists;
    }

    public void setMethodLists(Map<String, Object> methodLists) {
        this.methodLists = methodLists;
    }
    //------------------------------------------------------------

    protected  <T, R> R run(String status, T value){
        return run(status, value, null);
    }

    protected <T, U, R> R run(String status, T value, U value2){
        Object method = methodLists.get(status);
        //TODO: add ExceptionMessageCode in da
        validation().isNotNull(method).throwValidate(ExceptionMessageCode.GENERAL_NOT_THE_METHOD_NAME);

        R result = null;

        if(method instanceof Function){
            result = (R) ((Function) method).apply(value);
        }
        else if(method instanceof Consumer){
            ((Consumer) method).accept(value);
        }
        else if(method instanceof BiFunction){
            result = (R) ((BiFunction) method).apply(value, value2);
        }

        return result;
    }
}
