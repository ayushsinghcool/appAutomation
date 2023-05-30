package src.utils;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.globalConstant.FilePaths;
import src.initializers.AppPageInit;

import java.io.*;

public class CaptureADBLog extends AppPageInit {
    protected static LogEntries logcatLog;
    private static Logger logger = LoggerFactory.getLogger(CaptureADBLog.class);

    public static String captureLogcatLog() {
        logger.info("Capturing ADB Logs...");
        logcatLog = driver.manage().logs().get("logcat");
        String path = createTxtFile(FilePaths.LOGCAT_LOGS);

        try (PrintWriter logFileWriter = new PrintWriter(new FileWriter(FilePaths.LOGCAT_LOGS))) {
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
            if (file.exists()) {
                logger.info("File already exists.");
            }
            if (!directory.exists()) {
                directory.mkdir();
            } else {
                boolean fileCreated = file.createNewFile();
                logger.info("File Created : {}" ,fileCreated ? pathFileName : fileCreated);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathFileName;
    }
}
