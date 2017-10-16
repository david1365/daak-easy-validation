package da.interfaces;

import da.dto.ValidateMessageDTO;

import java.util.function.Function;

/**
 * Created by Akbari.David on 5/14/2017.
 */
public interface FunctionValidation <T> extends Function<T,ValidateMessageDTO> {
}
