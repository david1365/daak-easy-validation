package da.generality;

//import com.fanap.bpms.common.process.exception.ProcessException;
//import com.fanap.bpms.configuration.exception.InvalidConfigurationException;
//import com.fanap.bpms.exception.LanguageBundleException;
//import com.fanap.bpms.multilingual.LanguageBundleManager;
//import com.fanap.bpms.shared.ExceptionMessageHelper;
//import com.fanap.logger.LoggerService;
//import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by Akbari.David on 5/8/2017.
 */
public class Bundle {


//   static {
//       File file = new File("resource/lang/");
//   }
//    private static Logger logger = LoggerService.getLoggerInstance("Engine", ProcessException.class);
//
//    @SuppressWarnings("Duplicates")
//    public static String generateMessage(String errorKey, Object... params) {
//        try {
//            String e = LanguageBundleManager.getInstance().getLanguageBundleSourceFromConfig().toUpperCase();
//            String errorMsg = "";
//
//                LanguageBundleManager languageBundleManager = LanguageBundleManager.getInstance();
//                String currentLocale = "fa_IR";
//                errorMsg = languageBundleManager.getLanguageBundleValue(errorKey, currentLocale);
//                if(errorMsg == null) {
//                    errorMsg = errorKey;
//                }
//
//                return MessageFormat.format(errorMsg, params);
//
//
//        } catch (InvalidConfigurationException | IOException var6) {
//            logger.error("error while generating BusinessServiceException: errorKey:" + errorKey, var6);
//        }
//
//        return null;
//    }
//
//    public static String generateMessageFromBundle(String exceptionMessageCode, Object... params) {
//        return  generateMessage(ExceptionMessageHelper.getString(exceptionMessageCode), params);
//    }

//    public static Object[] generateAllMessagesFromBundles(Object... params){
//        Object[] newParams = new Object[params.length];
//
//        for (int i = 0; i < params.length; i++){
//            if (params[i] != null) {
//                newParams[i] = generateMessageFromBundle(params[i].toString());
//            }
//        }
//
//        return newParams;
//    }
}
