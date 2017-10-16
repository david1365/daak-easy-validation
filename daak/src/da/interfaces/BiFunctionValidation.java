package da.interfaces;

import da.dto.ValidateMessageDTO;

import java.util.function.BiFunction;

/**
 * Created by Akbari.David on 5/11/2017.
 */
public interface BiFunctionValidation <T, U> extends BiFunction<T, U, ValidateMessageDTO> {
}
