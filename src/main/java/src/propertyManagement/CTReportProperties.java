package src.propertyManagement;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class CTReportProperties {
    private CTReportProperties(){

    }
    public static final String PROPERTIES_FILE = "config/CTReportProperties.properties";
    public static final String FILE_NAME_PROPERTY = "CTReportProperties.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(CTReportProperties.class);
    private static final CTReportProperties CT = new CTReportProperties();
    private Properties properties = new Properties();

    public static final CTReportProperties getInstance() {
        CTReportProperties ctprop = CT;
        ctprop.init();
        return ctprop;
    }

    public String getProperty(String name) {
        return properties.getProperty(name);
    }

    private void setSystemProperties() {
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            String systemValue = System.getProperty(name);
            if (StringUtils.isBlank(systemValue)) {
                setProperty(name, StringUtils.trim(properties.getProperty(name)));
            } else {
                properties.put(name, systemValue);
            }
        }
    }

    public void setProperty(String name, String value) {
        properties.setProperty(name, value);
        System.setProperty(name, value);
    }

    private void loadProperties(InputStream propertiesStream) {
        try {
            properties.load(propertiesStream);
            setSystemProperties();
        } catch (Exception e) {
            LOGGER.error("Failed to load properties file", e);
        }
    }

    private InputStream getPropertiesFile() {
        String propertyFile = System.getProperty(FILE_NAME_PROPERTY, PROPERTIES_FILE);
        return this.getClass().getResourceAsStream("/" + propertyFile);
    }

    public void init() {
        loadProperties(getPropertiesFile());
    }
}
