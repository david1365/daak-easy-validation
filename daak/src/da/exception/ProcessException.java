package da.exception;

import da.generality.StringUtil;

public class ProcessException extends RuntimeException {
    public ProcessException(String message, Object[] parameters) {
        super(StringUtil.generateMessage(message, parameters));
    }

    public ProcessException(String message, Throwable cause, Object[] parameters) {
        super(StringUtil.generateMessage(message, parameters), cause);
    }

    public ProcessException(String message) {
        super(message);
    }

    public ProcessException(Throwable cause) {
        super(cause);
    }

    public ProcessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] parameters) {
        super(StringUtil.generateMessage(message, parameters), cause, enableSuppression, writableStackTrace);
    }
}
