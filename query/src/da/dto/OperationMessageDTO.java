package da.dto;

import java.util.function.Consumer;
import java.util.function.Function;

import static da.validation.Validation.*;

/**
 * Created by Akbari.David on 5/11/2017.
 */
public class OperationMessageDTO<T> {
    private String operationType;
    private T value;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public T getValue() {
        //TODO: complete this part whis ifValid and ifNotValid
        //validation().isNotNull(value).throwValidate(ExceptionMessageCode.GENERAL_GET_VALUE);

        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public OperationMessageDTO(String operationType, T value) {
        this(operationType);
        this.value = value;
    }

    public OperationMessageDTO(String operationType) {
        this.operationType = operationType;
    }

    public Boolean isNotNull() {
        return validation().isNotNull(value).isValid();
    }
    public void ifNotNull(Consumer<T> consumer) {
        validation().isNotNull(value).ifValid((Consumer<T>) (value)-> consumer.accept(value));
    }

    public <R> R ifNotNull(Function<T, R> function) {
        return validation().isNotNull(value).ifValid((Function<T, R>) (value)-> function.apply(value)).getIfResult();
    }

    public void ifNull(Consumer<T> consumer) {
        validation().isNotNull(value).ifNotValid((Consumer<T>) (value)-> consumer.accept(value));
    }

    public <R> R ifNull(Function<T, R> function) {
        return validation().isNotNull(value).ifNotValid((Function<T, R>) (value)-> function.apply(value)).getIfResult();
    }

}
