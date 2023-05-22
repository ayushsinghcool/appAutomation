package src.propertyManagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerCredentialsProperties {
    private ServerCredentialsProperties() {

    }

    private static final String PROPERTIES_FILE = "config/ServerCredentialsProperties.properties";
    private static Properties properties = new Properties();
    private static Logger logger = LoggerFactory.getLogger(ServerCredentialsProperties.class);

    static {

        logger.info("Loading Server Credentials Property");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            logger.error("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");

        }

        try {
            properties.load(propertiesFile);
        } catch (IOException e) {
            logger.error("Cannot load properties file :'" + PROPERTIES_FILE + "'");
        }
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }
}


