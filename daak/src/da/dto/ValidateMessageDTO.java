package da.dto;

import da.enums.ValidationType;
import da.generality.Bundle;
import da.generality.StringUtil;

/**
 * Created by Akbari.David on 5/9/2017.
 */
public class ValidateMessageDTO {
        private String validationType;
        private String message;
        private Object[] parameters;

        public String getValidationType() {
            return validationType;
        }

        public void setValidationType(String validationType) {
            this.validationType = validationType;
        }

        public String getMessage() {
            return message;
        }

        public final void setMessage(String message) {
            this.message = StringUtil.nvl(message);
        }

        public Object[] getParameters() {
            return parameters;
        }

        public void setParameters(Object[] parameters) {
            this.parameters = parameters;
            this.setMessage(this.message);
        }

        public ValidateMessageDTO(String validationType) {
            this.validationType = validationType;
        }

        public ValidateMessageDTO(String validationType, String message) {
            this(validationType);
            //this.message = generateMessageFromBundle(message);
            setMessage(message);
        }

        public ValidateMessageDTO(String validationType, String message, Object... parameters) {
            this(validationType, message);
            this.parameters = parameters;
        }

        public Boolean isValid(){
            if (this.validationType == ValidationType.GENERAL_VALID){
                return true;
            }

            return false;
        }

        @Override
        public String toString() {
            return validationType.toString();

        }
}
