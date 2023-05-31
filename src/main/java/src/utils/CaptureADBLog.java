package src.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.globalConstant.FilePaths;
import src.initializers.AppPageInit;
import src.reportManagement.ExtentManager;

import java.io.*;

public class CaptureADBLog extends AppPageInit {
    protected static LogEntries logcatLog;
    private static Logger logger = LoggerFactory.getLogger(CaptureADBLog.class);

    private static String path;

    public static String captureLogcatLog() {
        logger.info("Capturing ADB Logs...");
        logcatLog = driver.manage().logs().get("logcat");
        FilePaths.setFileName();
        path = createTxtFile(FilePaths.getFileName());

        try (PrintWriter logFileWriter = new PrintWriter(new FileWriter(path))) {
            for (LogEntry entry : logcatLog) {
                if (entry.getMessage().contains("okhttp.OkHttpClient:") || entry.getMessage().contains("pai_network")) {
                    logFileWriter.println(entry.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String createTxtFile(String pathFileName) {
        try {
            File file = new File(pathFileName);
            File directory = new File(FilePaths.LOGS);
            if (!directory.exists()) {
                directory.mkdir();
            }
            if (file.exists()) {
                logger.info("File already exists.");
            }else {
                boolean fileCreated = file.createNewFile();
                logger.info("File Created : {}" ,fileCreated ? pathFileName : fileCreated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathFileName;
    }

//    public static String[]  fetchReqRes(String regex){
//        ExtentTest node = ExtentManager.getTest();
//        String line;
//        String request = null;
//        String response = null;
//        try{
//            FileReader fr = new FileReader(path);
//            BufferedReader br = new BufferedReader(fr);
//            while((line =  br.readLine()) != null){
//                if (line.contains(regex))
//                {
//                     // {"head":{"responseTimestamp":"2023-05-3016:24:00"},"body":{"resultStatus":"SUCCESS","resultcode":"S","resultMsg":"Terminal already initiated","bankTid":"5P002524","bankMid":"5PT000000000026","mid":"DvFnKU85682298907046","merchantName":"TOUCH WOOD LIMITED","merchantAddress":"H/1","mccCode":"1234"}}
//                    // {"body":{"serialNo":"149015901978438","stan":"000001","tid":"14015808","vendorName":"PAX"},"head":{"clientId":"PAXA9023453","firmwareVersion":"A910_PayDroid_6.0_Leo_V07.1.23_20211224","isP2PEDevice":false,"osVersion":"6.0","version":"2.4.9"}}
//                    request = "Request : "+"{\"body\":"+line.split("\"body\":")[1];
//                    response = "Response : "+"{\"head\":"+line.split("\"head\":")[1];
//                   // System.out.println(line.split(""));
//                    break;
//                }
//            }
//
//        }
//        catch (Exception e){
//            node.fail("Unable to Find Request/Response :- ");
//            node.log(Status.INFO,"StackTrace Result: " + ExceptionUtils.getStackTrace(e));
//        }
//        return new String[]{request,response};
//    }

    public static String[] fetchReqRes(String regex) {
        ExtentTest node = ExtentManager.getTest();
        String line;
        String request = null;
        String response = null;

        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {

            while ((line = br.readLine()) != null) {
                if (line.contains(regex)) {
                    // Extract request and response JSON data
                    request = "Request : "+"{\"body\":"+line.split("\"body\":")[1];
                    response = "Response : "+"{\"head\":"+line.split("\"head\":")[1];
                    break;

                }
            }

        } catch (IOException e) {
            node.fail("Unable to Find Request/Response");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }

        return new String[]{request, response};
    }


}
