package src.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        String adbLogPath = "reports/logs/";
        String fileName =  "adbLog_"+DateUtil.getTimeStamp()+".txt";
        path = Utils.createTxtFile(adbLogPath,fileName);

        try (PrintWriter logFileWriter = new PrintWriter(new FileWriter(path))) {
            for (LogEntry entry : logcatLog) {
                if (entry.getMessage().contains("okhttp.OkHttpClient:") || entry.getMessage().contains("pai_network")) {
                    logFileWriter.println(entry.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "../logs/"+fileName;
    }

    public static String[] fetchReqRes(String regex,int index) {
        ExtentTest node = ExtentManager.getTest();
        String line;
        String request = null;
        String response = null;

        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {

            while ((line = br.readLine()) != null) {
                if (line.contains(regex)) {
                    // Extract request and response JSON data
                    request = line.split("\"body\":")[index];
                    response =line.split("\"head\":")[index];
                    break;

                }
            }

        } catch (IOException e) {
            node.fail("Unable to Find Request/Response");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }

        return new String[]{request, response};
    }

    public static String fetchLog(String regex) {

        ExtentTest node = ExtentManager.getTest();

        String line = null;

        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {

            while ((line = br.readLine()) != null) {
                if (line.matches(regex)) {

                    break;
                }
            }

        } catch (IOException e) {
            node.info("Unable to Find Request/Response");
            node.log(Status.INFO, "StackTrace Result: " + ExceptionUtils.getStackTrace(e));
        }

        return line;
    }

    }
