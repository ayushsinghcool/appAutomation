package src.globalConstant;

import src.propertyManagement.CTReportProperties;
import src.propertyManagement.MobileProperties;
import src.utils.DateUtil;

import java.io.File;

public class FilePaths {

    private  static final String USER_DIR = "user.dir";

    private FilePaths() {

    }

    static final String BASE_PATH = new File("").getAbsolutePath();
    public static final String APPIUM_JS_PATH = MobileProperties.getProperty("os.platform").equalsIgnoreCase("mac")
            ? "/opt/homebrew/bin/appium"
            : System.getProperty("user.home") + "\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
    public static final String NODE_JS_PATH = MobileProperties.getProperty("os.platform").equalsIgnoreCase("mac")
            ? "/opt/homebrew/bin/node"
            : "C:\\Program Files\\nodejs\\node.exe";
    public static final String EXTENT_REPORT_PATH = BASE_PATH +File.separator+ "reports"+File.separator+"htmlReports"+File.separator;
    public static final String APPLICATION_PATH = System.getProperty(USER_DIR) + File.separator + "apps" + File.separator;

    public  static final String LOGS = System.getProperty(USER_DIR) + File.separator+"reports"+File.separator+"logs"+File.separator;

    public static final  String CT_REPORT = System.getProperty(USER_DIR) + File.separator+"CTReports"+File.separator;
    public static final String CIT_REPORT_OUTPUT_FILE = CT_REPORT + CTReportProperties.getInstance().getProperty("BUILDID") + "_"
            + CTReportProperties.getInstance().getProperty("TestFrameworkName") + "_" +
            CTReportProperties.getInstance().getProperty("ProductInterface")
            + "_" + DateUtil.getCurrentDateTimeExcel() + ".xlsx";

    private static String logcatLogs;

    public static void setFileName(){
        logcatLogs =LOGS + "adbLog_"+DateUtil.getTimeStamp()+".txt";
    }
    public static String getFileName(){
        return logcatLogs;
    }

}
