package src.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.propertyManagement.MobileProperties;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageReader {

    private MessageReader(){

    }

    private static Logger logger = LoggerFactory.getLogger(MessageReader.class);
    private static ResourceBundle resourceBundle;
    private static String languageCode;
    private static String countryCode;
    private static Locale locale;

    static {
        logger.info("Message Reader Loaded...");
        languageCode = MobileProperties.getProperty("language.code",Locale.ENGLISH.getLanguage());
        countryCode = MobileProperties.getProperty("country.code",Locale.ENGLISH.getCountry());
        locale = new Locale(languageCode,countryCode);
        loadResourceBundle();
    }

    private static void loadResourceBundle(){
        if(resourceBundle == null){
            resourceBundle = ResourceBundle.getBundle("messages",locale);
        }
    }

    public static String getMessage( String key)  {
        return  resourceBundle.getString(key);
    }

    public static String getDynamicMessage(String key,String... params) {
        String msg = getMessage(key);
        return MessageFormat.format(msg,params);
    }

//    public static void main(String[] args) {
//        MessageReader messageReader = new MessageReader();
//        System.out.println(getMessage("VALIDATION_MESSAGE_0001"));
//    }
}
