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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaptureADBLog extends AppPageInit{
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
            } else {
                boolean fileCreated = file.createNewFile();
                logger.info("File Created : {}", fileCreated ? pathFileName : fileCreated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathFileName;
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

    public static String[] fetchReqRes(String requestRegex, String responseRegex) {
        ExtentTest node = ExtentManager.getTest();
        String request = null;
        String response = null;

        try (FileReader fr = new FileReader(path);
             BufferedReader br = new BufferedReader(fr)) {

            Pattern requestPattern = Pattern.compile(requestRegex);
            Pattern responsePattern = Pattern.compile(responseRegex);

            String line;
            while ((line = br.readLine()) != null) {
                Matcher requestMatcher = requestPattern.matcher(line);
                Matcher responseMatcher = responsePattern.matcher(line);

                if (requestMatcher.find()) {
                    request = requestMatcher.group(1); // or group(0) depending on the capturing group in your regex
                }

                if (responseMatcher.find()) {
                    response = responseMatcher.group(1); // or group(0) depending on the capturing group in your regex
                }

                // If both request and response are found, break from the loop
                if (request != null && response != null) {
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
