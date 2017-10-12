package da.status;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * Created by Akbari.David on 5/24/2017.
 */
public class StatusManager extends MethodManager {

    public static StatusManager statusManager(){
        StatusManager me = new StatusManager();

        return me;
    }

    public <T> StatusManager addMethod(String status, Consumer<T> consumerMethod){
        methodLists.put(status.toString(), consumerMethod);

        return this;
    }

    public <T, R> StatusManager addMethod(String status, Function<T, R> functionMethod){
        methodLists.put(status.toString(), functionMethod);

        return this;
    }

    public <T, U, R> StatusManager addMethod(String status, BiFunction<T, U, R> functionMethod){
        methodLists.put(status.toString(), functionMethod);

        return this;
    }

//------------------------------------------------------------
    public <T, R> R executeMethod(String status, T value){
        return run(status, value, null);
    }

    public  <T, U, R> R executeMethod(String status, T value, U value2){
        return run(status, value, value2);
    }

}
